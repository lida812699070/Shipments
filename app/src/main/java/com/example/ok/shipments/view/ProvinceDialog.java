package com.example.ok.shipments.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.ok.shipments.MyAppLocation;
import com.example.ok.shipments.R;
import com.example.ok.shipments.adapter.ItemSimpleAdapterAdapter;
import com.example.ok.shipments.model.CityModel;
import com.example.ok.shipments.model.DistrictModel;
import com.example.ok.shipments.model.ProvinceModel;
import com.example.ok.shipments.utils.StrKit;
import com.example.ok.shipments.widget.OnWheelChangedListener;
import com.example.ok.shipments.widget.WheelView;
import com.example.ok.shipments.widget.adapters.ArrayWheelAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProvinceDialog extends Dialog implements OnClickListener, OnWheelChangedListener {
    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    protected Map<String, String> mCitisAreaCodeMap = new HashMap<String, String>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName = "";

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode = "";
    protected String mCurrentAreaCode = "";

    /**
     * 解析省市区的XML数据
     */

    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private Button mBtnConfirm;
    private Button mBtnCancel;
    private Context mContext;
    private OnProvinceClick mOnProvinceClick;
    private String lastProvince;
    private String lastCity;
    private String lastDistrict;
    private SearchView etShare;
    private List<ProvinceModel> provinceList;
    private int pCurrent;
    private int cCurrent;
    private ListView listView;
    private ArrayList<String> list;
    private ItemSimpleAdapterAdapter adapter;
    private List<CityModel> cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.province_dialog);
        LayoutParams params = getWindow().getAttributes();
        params.width = LayoutParams.FILL_PARENT;
        getWindow().setAttributes((LayoutParams) params);
        this.setTitle("请选择省市区");
        setUpViews();
        setUpListener();
        setUpData();
        //更新城市信息
        if (StrKit.notBlank(lastProvince)) {
            for (int i = 0; i < mProvinceDatas.length; i++)
                if (mProvinceDatas[i].equals(lastProvince)) {
                    mViewProvince.setCurrentItem(i);
                    break;
                }

            updateCities();
            String[] cities = mCitisDatasMap.get(lastProvince);
            for (int i = 0; i < cities.length; i++)
                if (cities[i].equals(lastCity)) {
                    mViewCity.setCurrentItem(i);
                    break;
                }

            updateAreas();
            String[] areas = mDistrictDatasMap.get(lastCity);
            for (int i = 0; i < areas.length; i++)
                if (areas[i].equals(lastDistrict)) {
                    mViewDistrict.setCurrentItem(i);
                    break;
                }
        }
    }

    public ProvinceDialog(Context context, String last) {
        super(context);
        mContext = context;
        if (StrKit.notBlank(last)) {
            String[] strs = last.split(",");
            if (strs.length > 0)
                lastProvince = strs[0];
            if (strs.length > 1)
                lastCity = strs[1];
            if (strs.length > 2)
                lastDistrict = strs[2];
        }
    }

    public void setListener(OnProvinceClick i) {
        mOnProvinceClick = i;
    }

    private void setUpViews() {
        mViewProvince = (WheelView) findViewById(R.id.id_province);
        mViewCity = (WheelView) findViewById(R.id.id_city);
        mViewDistrict = (WheelView) findViewById(R.id.id_district);
        mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
        mBtnCancel = (Button) findViewById(R.id.btn_cancel);
        etShare = (SearchView) findViewById(R.id.search_view);
        listView = (ListView) findViewById(R.id.lv);
        list = new ArrayList<String>();
        adapter = new ItemSimpleAdapterAdapter<>(mContext,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProvinceModel provinceModel = null;
                CityModel cityModel = null;
                String str = list.get(position);
                String[] split = str.split(",");
                switch (split.length) {
                    case 1:
                        itemSelect1(split);
                        break;
                    case 2:
                        itemSelect1(split);
                        itemSelect2(provinceModel, split);
                        break;
                    case 3:
                        itemSelect1(split);
                        itemSelect2(provinceModel, split);
                        itemSelect3(cityModel, split);
                        mBtnConfirm.performClick();
                        break;
                }
                list.clear();
                adapter.notifyDataSetChanged();
            }
        });
        //etSharecity = (EditText) findViewById(R.id.edit_query_str_city);
        //etDistrict = (EditText) findViewById(R.id.edit_query_str_district);
    }

    private void itemSelect3(CityModel cityModel, String[] other) {
        cityModel = cityList.get(currCity);
        List<DistrictModel> districtList = cityModel.getDistrictList();
        for (int i = 0; i < districtList.size(); i++) {
            DistrictModel districtModel = districtList.get(i);
            if (districtModel.getName().equals(other[2])) {
                mViewDistrict.setCurrentItem(i);
            }
        }
    }

    private int currCity = 0;

    private void itemSelect2(ProvinceModel provinceModel, String[] other) {
        CityModel cityModel;
        provinceModel = provinceList.get(currpro);
        cityList = provinceModel.getCityList();
        for (int i = 0; i < cityList.size(); i++) {
            cityModel = cityList.get(i);
            if (cityModel.getName().equals(other[1])) {
                mViewCity.setCurrentItem(i);
                currCity = i;
                return;
            }
        }
    }

    private int currpro = 0;

    private void itemSelect1(String[] other) {
        ProvinceModel provinceModel;
        for (int i = 0; i < provinceList.size(); i++) {
            provinceModel = provinceList.get(i);
            String name = provinceModel.getName();
            if (name.equals(other[0])) {
                mViewProvince.setCurrentItem(i);
                currpro = i;
                return;
            }
        }
    }

    private int proIndex, cityIndex, disIndex;

    private void setUpListener() {
        mViewProvince.addChangingListener(this);
        mViewCity.addChangingListener(this);
        mViewDistrict.addChangingListener(this);
        mBtnConfirm.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
        etShare.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                list.clear();
                if (TextUtils.isEmpty(query)) {
                    return false;
                }
                for (int i = 0; i < provinceList.size(); i++) {
                    ProvinceModel provinceModel = provinceList.get(i);
                    String name = provinceModel.getName();
                    if (name.contains(query)) {
                        mViewProvince.setCurrentItem(i);
                        proIndex = i;
                        list.add(name);
                    }
                    int size = provinceModel.getCityList().size();
                    for (int j = 0; j < size; j++) {
                        CityModel cityModel = provinceModel.getCityList().get(j);
                        String cityName = cityModel.getName();
                        if (cityName.contains(query)) {
                            mViewProvince.setCurrentItem(i);
                            mViewCity.setCurrentItem(j);
                            cityIndex = j;
                            list.add(name + "," + cityName);
                        }
                        int districtSize = cityModel.getDistrictList().size();
                        for (int k = 0; k < districtSize; k++) {
                            DistrictModel districtModel = cityModel.getDistrictList().get(k);
                            String districtModelName = districtModel.getName();
                            if (districtModelName.contains(query)) {
                                mViewProvince.setCurrentItem(i);
                                mViewCity.setCurrentItem(j);
                                mViewDistrict.setCurrentItem(k);
                                disIndex = k;
                                list.add(name + "," + cityName + "," + districtModelName);
                            }
                        }
                    }
                }
                adapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(mContext, mProvinceDatas));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(mContext, areas));
        mViewDistrict.setCurrentItem(0);
        mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
        mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        mCurrentAreaCode = mCitisAreaCodeMap.get(mCurrentCityName);
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        cCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[cCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(mContext, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                showSelectedResult();
                this.dismiss();
                break;
            case R.id.btn_cancel:
                ProvinceDialog.this.dismiss();
                break;
            default:
                break;
        }
    }

    private void showSelectedResult() {
        if (mOnProvinceClick != null)
            mOnProvinceClick.onData(mCurrentProviceName + "," + mCurrentCityName + "," + mCurrentDistrictName, mCurrentAreaCode, mCurrentZipCode);
    }

    protected void initProvinceDatas() {
            /*AssetManager asset = mContext.getAssets();
            try {
	            InputStream input = asset.open("province_data.xml");
	            // 创建一个解析xml的工厂对象
				SAXParserFactory spf = SAXParserFactory.newInstance();
				// 解析xml
				SAXParser parser = spf.newSAXParser();
				XmlParserHandler handler = new XmlParserHandler();
				parser.parse(input, handler);
				input.close();
				// 获取解析出来的数据
				provinceList = handler.getDataList();*/
        provinceList = MyAppLocation.getApplication().getProvinceList();
        //*/ 初始化默认选中的省、市、区
        if (provinceList != null && !provinceList.isEmpty()) {
            mCurrentProviceName = provinceList.get(0).getName();
            List<CityModel> cityList = provinceList.get(0).getCityList();
            if (cityList != null && !cityList.isEmpty()) {
                mCurrentCityName = cityList.get(0).getName();
                List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                mCurrentDistrictName = districtList.get(0).getName();
                mCurrentZipCode = districtList.get(0).getZipcode();
            }
        }
        //*/
        mProvinceDatas = new String[provinceList.size()];
        for (int i = 0; i < provinceList.size(); i++) {
            // 遍历所有省的数据
            mProvinceDatas[i] = provinceList.get(i).getName();
            List<CityModel> cityList = provinceList.get(i).getCityList();
            String[] cityNames = new String[cityList.size()];
            for (int j = 0; j < cityList.size(); j++) {
                // 遍历省下面的所有市的数据
                cityNames[j] = cityList.get(j).getName();
                this.mCitisAreaCodeMap.put(cityList.get(j).getName(), cityList.get(j).getAreaCode());
                List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                String[] distrinctNameArray = new String[districtList.size()];
                DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                for (int k = 0; k < districtList.size(); k++) {
                    // 遍历市下面所有区/县的数据
                    DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                    // 区/县对于的邮编，保存到mZipcodeDatasMap
                    mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                    distrinctArray[k] = districtModel;
                    distrinctNameArray[k] = districtModel.getName();
                }
                // 市-区/县的数据，保存到mDistrictDatasMap
                mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
            }
            // 省-市的数据，保存到mCitisDatasMap
            mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
        }
            /*} catch (Throwable e) {
                e.printStackTrace();
	        } finally {
	        	
	        } */
    }

    public interface OnProvinceClick {
        public void onData(String province, String areaCode, String code);
    }
}
