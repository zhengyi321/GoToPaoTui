package com.zoutu.gotopaotui.ActivityMain.FragmentOrderCenter;

import com.zoutu.gotolibrary.Bean.AngleGetOrderBean;
import com.zoutu.gotolibrary.Bean.AngleOrderBean;
import com.zoutu.gotolibrary.Utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/4/11.
 */

public class MainOrderCenterFragmentUtils {

    /**
     * 冒泡法排序<br/>

     * <li>比较相邻的元素。如果第一个比第二个大，就交换他们两个。</li>
     * <li>对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。</li>
     * <li>针对所有的元素重复以上的步骤，除了最后一个。</li>
     * <li>持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。</li>

     *
     * @param numbers
     *            需要排序的整型数组
     */
    public  void bubbleSort(int[] numbers) {
        int temp; // 记录临时中间值
        int size = numbers.length; // 数组大小
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                if (numbers[i] < numbers[j]) { // 交换两数的位置
                    temp = numbers[i];
                    numbers[i] = numbers[j];
                    numbers[j] = temp;
                }
            }
        }
    }

    public List<AngleOrderBean> bubbleSortOrderCenterBean(List<AngleOrderBean> angleOrderBeanList){
        List<AngleOrderBean> angleOrderBeanArrayList = new ArrayList<>(); // 记录临时中间值
        int size = angleOrderBeanList.size(); // 数组大小
        TimeUtil timeUtil = new TimeUtil();
        AngleOrderBean temp =  angleOrderBeanList.get(0);
        try {


            for (int i = 0; i < size; i++) {
                for (int j = 0; j < angleOrderBeanList.size(); j++) {
                    if (timeUtil.dateCompare(temp.getOrderOrdertime(), angleOrderBeanList.get(j).getOrderOrdertime()) < 0) {
                        temp = angleOrderBeanList.get(j);
                    }
                }
                angleOrderBeanArrayList.add(temp);
                angleOrderBeanList.remove(temp);
            }
        }catch (Exception e){

        }
        return angleOrderBeanArrayList;
    }
}
