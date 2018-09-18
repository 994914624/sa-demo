package cn.sensorsdata.demo.fragment;


import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackAppViewScreenUrl;
import com.sensorsdata.analytics.android.sdk.SensorsDataTrackEvent;
import com.sensorsdata.analytics.android.sdk.SensorsExpandableListViewItemTrackProperties;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sensorsdata.demo.R;

/**
 * A simple {@link Fragment} subclass.
 */
@SensorsDataAutoTrackAppViewScreenUrl(url="xxx.myFrg页面")
public class MyFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemClickListener {

    ExpandableListView mainlistview = null;
    List<String> parent = null;
    Map<String, List<String>> map = null;
    SwitchCompat switchCompat3=null;


    private View view = null;
    private Context context=null;
    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view=inflater.inflate(R.layout.fragment_my, container, false);
        context=getActivity();
       initSwitchCompat();

        return view;
    }

    private void initSwitchCompat() {

        /**
         *SwitchCompat
         */

        final SwitchCompat switchCompat= (SwitchCompat)view.findViewById(R.id.switchCompat);
        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            @TargetApi(14)
            public void onClick(View view) {
                Toast.makeText(context,""+switchCompat.isChecked(),Toast.LENGTH_SHORT).show();

                switchCompat.setChecked(switchCompat.isChecked());
            }
        });

        SwitchCompat switchCompat2= (SwitchCompat)view.findViewById(R.id.switchCompat2);
        switchCompat2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(context,"sc",Toast.LENGTH_SHORT).show();
            }
        });
        switchCompat3= (SwitchCompat)view.findViewById(R.id.switchCompat3);
        switchCompat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog1();
                Toast.makeText(context,""+switchCompat3.isChecked(),Toast.LENGTH_SHORT).show();
            }
        });


        /*
         *context menu
         */
        Button button_menu = (Button)view.findViewById(R.id.button_menu);
        registerForContextMenu(button_menu);

        /*
         *seekBar、 spinner、ratingBar
         */
        SeekBar seekBar = (SeekBar)view.findViewById(R.id.yang_seekBar);
        Spinner spinner = (Spinner)view.findViewById(R.id.yang_spinner);
        RatingBar ratingBar = (RatingBar)view.findViewById(R.id.yang_ratingBar);
        //ratingBar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                Toast.makeText(context, "onStopTrackingTouch", Toast.LENGTH_SHORT).show();
            }
        });
        //ratingBar
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                Toast.makeText(context, "onRatingChanged", Toast.LENGTH_SHORT).show();
            }
        });

        //spinner ？？？
        String[] mItems = {"北京", "上海", "深圳", "美国", "清华"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mItems);
        spinner.setAdapter(adapter);
        spinner.setSelection(0,false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context, "" + i, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        /*
         *dialog
         */
        showNormalDialog();




        /*
         *ToggleButton
         */
        final ToggleButton toggleButton = (ToggleButton)view.findViewById(R.id.toggleButton);
//        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                toggleButton.setChecked(b);
//            }
//        });

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,""+toggleButton.isChecked(),Toast.LENGTH_SHORT).show();
                toggleButton.setChecked(toggleButton.isChecked());
            }
        });

        ToggleButton toggle2= (ToggleButton)view.findViewById(R.id.toggleButton2);

        toggle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                toggleButton.setChecked(b);
                Toast.makeText(context,"2"+toggleButton.isChecked(),Toast.LENGTH_SHORT).show();

            }
        });



        /*
         *CheckedTextView
         */
        final CheckedTextView checkedTextView = (CheckedTextView)view.findViewById(R.id.checkedTextView);
        checkedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkedTextView.toggle();
            }
        });


        /*
         *CheckBox、RadioButton
         */
        CheckBox checkBox = (CheckBox)view.findViewById(R.id.yang_checkBox);
        CheckBox checkBox000 = (CheckBox)view.findViewById(R.id.yang_checkBox000);
        RadioButton radioButton = (RadioButton)view.findViewById(R.id.yang_radioButton);
        RadioButton radioButton000 = (RadioButton)view.findViewById(R.id.yang_radioButton000);
        RadioButton radioButton111 = (RadioButton)view.findViewById(R.id.yang_radioButton111);
        RadioButton radioButton01 = (RadioButton)view.findViewById(R.id.yang_radioButton01);
        RadioButton radioButton02 = (RadioButton)view.findViewById(R.id.yang_radioButton02);

        RadioGroup radioGroup = (RadioGroup)view.findViewById(R.id.yang_RadioGroup);
        RadioGroup radioGroup222 = (RadioGroup)view.findViewById(R.id.radio_group222);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Toast.makeText(context, "rg" + i, Toast.LENGTH_SHORT).show();
            }
        });
        radioGroup222.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Toast.makeText(context, "rg" + i, Toast.LENGTH_SHORT).show();
            }
        });


//        radioButton.setOnCheckedChangeListener(this);
//        radioButton01.setOnCheckedChangeListener(this);
//        radioButton02.setOnCheckedChangeListener(this);

        radioButton000.setOnClickListener(this);
        radioButton111.setOnCheckedChangeListener(this);

        checkBox000.setOnClickListener(this);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(context, "checkBox", Toast.LENGTH_SHORT).show();
            }
        });

        //checkBox.setChecked();


        /*
         *Button、TextView、ImageView、ImageButton
         */
        Button button = (Button)view.findViewById(R.id.yang_button);
        TextView textView = (TextView)view.findViewById(R.id.yang_textView);
        ImageView imageView = (ImageView)view.findViewById(R.id.yang_imageView);
        ImageButton imageButton = (ImageButton)view.findViewById(R.id.yang_imageButton);

        button.setOnClickListener(this);
        textView.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        imageView.setOnClickListener(this);


        /*
         *setViewID
         *
         */
        // SensorsDataAPI.sharedInstance(this).ignoreView(checkBox);

        SensorsDataAPI.sharedInstance().setViewID(checkBox, "yyyyyyyyyyy");
        SensorsDataAPI.sharedInstance().setViewID(checkBox, "zzzzzzzzzzz");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("orderId", "888888");
            jsonObject.put("manufacturer", "sensorsdata");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject02 = new JSONObject();
        try {
            jsonObject02.put("yang", "666666");
            jsonObject02.put("zhan", "sensorsdata");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        SensorsDataAPI.sharedInstance().setViewProperties(checkBox, jsonObject);
        SensorsDataAPI.sharedInstance().setViewProperties(textView, jsonObject);
        SensorsDataAPI.sharedInstance().setViewProperties(checkBox, jsonObject02);


        /*
         *ExpandableListView
         *
         */
        mainlistview = (ExpandableListView)view.findViewById(R.id.yang_expandablelistview);
        initData();
        mainlistview.setAdapter(new YangAdapter());
        mainlistview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Toast.makeText(getContext(),"onChildClick",Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        mainlistview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                Toast.makeText(getContext(),"onGroupClick",Toast.LENGTH_SHORT).show();

                return false;
            }
        });


        /**
         *ListView
         */

        ListView listView= (ListView)view.findViewById(R.id.yang_listView);
        String [] arr={"yyy","zzz","kkk","sss"};
        listView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_expandable_list_item_1,arr));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"ListView"+position,Toast.LENGTH_SHORT).show();
            }
        });



        /**
         *GridView
         */
        String [] arr2={"y","z","k","s"};
        GridView gridView= (GridView)view.findViewById(R.id.yang_gridView);
        gridView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_expandable_list_item_1,arr2));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"GridView"+position,Toast.LENGTH_SHORT).show();
            }
        });


    }


    /*
    *Dialog
    */
    private void showNormalDialog() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(context);

        normalDialog.setIcon(R.mipmap.ic_launcher);
        normalDialog.setTitle("我是一个普通Dialog");
        normalDialog.setMessage("你要点击哪一个按钮呢?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        //
        //normalDialog.create().setOwnerActivity(this);
        // 显示
        normalDialog.show().setOwnerActivity(getActivity());

//        AlertDialog dialog =  normalDialog.create();
//        SensorsDataAPI.sharedInstance().trackAppDialog(this, dialog);
//        dialog.show();
        //AlertDialog dialog=new AlertDialog(this);

    }

    /*
     *Dialog1
     */
    private void dialog1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);  //先得到构造器
        //builder.create().setOwnerActivity(this);
        builder.setTitle("提示"); //设置标题
        builder.setMessage("是否确认退出?"); //设置内容
        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); //关闭dialog
                Toast.makeText(context, "确认" + which, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(context, "取消" + which, Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNeutralButton("忽略", new DialogInterface.OnClickListener() {//设置忽略按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(context, "忽略" + which, Toast.LENGTH_SHORT).show();
            }
        });

        Dialog dia=builder.create();

        //参数都设置完成了，创建并显示出来
        dia.show();
        //SensorsDataAPI.sharedInstance().trackAppDialog(getActivity(), dia);

    }



    /*
     *RadioButton onCheckedChanged
     */
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Toast.makeText(context, "radioButton", Toast.LENGTH_SHORT).show();

    }


    /*
     *context menu----------------------
     *
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("文件操作");
        // add context menu item
        menu.add(0, 1, Menu.NONE, "发送");
        menu.add(0, 2, Menu.NONE, "标记为重要");
        menu.add(0, 3, Menu.NONE, "重命名");
        menu.add(0, 4, Menu.NONE, "删除");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Toast.makeText(context,"context menu",Toast.LENGTH_SHORT).show();
        return super.onContextItemSelected(item);
    }

    /**
     * option menu
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu, menu);

        super.onCreateOptionsMenu(menu, inflater);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(context, "添加", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(context, "删除", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /*
         *ExpandableListView、初始化数据
         *
         */
    public void initData() {
        parent = new ArrayList<String>();
        parent.add("parent1");
        parent.add("parent2");
        parent.add("parent3");

        map = new HashMap<String, List<String>>();

        List<String> list1 = new ArrayList<String>();
        list1.add("child1-1");
        list1.add("child1-2");
        list1.add("child1-3");
        map.put("parent1", list1);

        List<String> list2 = new ArrayList<String>();
        list2.add("child2-1");
        list2.add("child2-2");
        list2.add("child2-3");
        map.put("parent2", list2);

        List<String> list3 = new ArrayList<String>();
        list3.add("child3-1");
        list3.add("child3-2");
        list3.add("child3-3");
        map.put("parent3", list3);

    }

    /**
     *ListView,GridView  Item点击
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Toast.makeText(getContext(),"点击"+i,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getContext(),"点击",Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public String getScreenUrl() {
//        return "my/fragment";
//    }
//
//    @Override
//    public JSONObject getTrackProperties() throws JSONException {
//        return null;
//    }


    /*
     *ExpandableListView Adapter
     *
     */
    class YangAdapter extends BaseExpandableListAdapter implements SensorsExpandableListViewItemTrackProperties {

        //得到子item需要关联的数据
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            String key = parent.get(groupPosition);
            return (map.get(key).get(childPosition));
        }

        //得到子item的ID
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        //设置子item的组件
        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup par) {
            String key = parent.get(groupPosition);
            String info = map.get(key).get(childPosition);
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.yang_expandable_child, null);
            }
            TextView tv = (TextView) convertView
                    .findViewById(R.id.yang_child_textview);
            tv.setText(info);
//            tv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(context,"11",Toast.LENGTH_SHORT).show();
//                }
//            });
            return tv;
        }

        //获取当前父item下的子item的个数
        @Override
        public int getChildrenCount(int groupPosition) {
            String key = parent.get(groupPosition);
            int size = map.get(key).size();
            return size;
        }

        //获取当前父item的数据
        @Override
        public Object getGroup(int groupPosition) {
            return parent.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return parent.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        //设置父item组件
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup par) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.yang_expandable_parent, null);
            }
            TextView tv = (TextView) convertView
                    .findViewById(R.id.yang_parent_textview);
            tv.setText(parent.get(groupPosition));
//            tv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(context,"parent",Toast.LENGTH_SHORT).show();
//                }
//            });
            return tv;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }


        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public JSONObject getSensorsChildItemTrackProperties(int i, int i1) throws JSONException {
            JSONObject properties = new JSONObject();
            properties.put("children", i + i1);                    // 设置商品ID
            properties.put("ProductCatalog", "Laptop Computer");    // 设置商品类别
            JSONObject isAddedToFav = properties.put("IsAddedToFav", false);// 是否被添加到收藏夹
            return properties;
        }

        @Override
        public JSONObject getSensorsGroupItemTrackProperties(int i) throws JSONException {
            JSONObject properties = new JSONObject();
            properties.put("parent", i);                    // 设置商品ID
            properties.put("ProductCatalog", "Laptop Computer");    // 设置商品类别
            properties.put("IsAddedToFav", false);                  // 是否被添加到收藏夹
            return properties;
        }
    }





//    @SensorsDataTrackViewOnClick
//    public void onTest(View view){
//        Toast.makeText(context,"onTest",Toast.LENGTH_SHORT).show();
//        // showNormalDialog();
//        //dialog1();
//        //someMethod();
////
//        //SensorsDataAPI.sharedInstance(this).ignoreViewType(ListView.class);
//        // showNormalDialog();
//    }
//
//    public void onTest02(View view){
//        //SensorsDataAPI.sharedInstance(this).ignoreAutoTrackEventType(SensorsDataAPI.AutoTrackEventType.APP_CLICK);
//
//        // Toast.makeText(this,":"+switchCompat3.isChecked(),Toast.LENGTH_SHORT).show();
//    }

    @SensorsDataTrackEvent(eventName = "someEventName", properties = "{\"provider\":\"神策数据\",\"number\":100,\"isLogin\":true}")
    public void someMethod() {


    }


//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//    }
//
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//    }
}
