package com.aura.bluetoothphone.utils.connects;

import java.util.Arrays;
import java.util.List;

import android.util.SparseIntArray;
import android.widget.SectionIndexer;

import com.aura.bluetoothphone.bean.ContactsPhoneBean;


/**
 * @ClassName: CustomSectionIndexer
 * @Description: TODO
 * @author: steven zhang
 * @date: Sep 27, 2016 2:32:26 PM
 */
public class CustomSectionIndexer implements SectionIndexer {

	private List<ContactsPhoneBean> mDataList;
	private SparseIntArray mSectionPos;
	//section position letter
	private StringBuilder mSectionBuilder;
	private int mCount;
	private int[] mPositions;
	private int mSectionSize;
	
	public CustomSectionIndexer(List<ContactsPhoneBean> list) {
		
		mSectionPos = new SparseIntArray();
		mSectionBuilder = new StringBuilder();
		
		mDataList = list;
		int j = 0;
		mCount = mDataList.size();
		for (int i = 0; i < mCount; i++) {
			String curLetter = mDataList.get(i).getFirstLetter();
			String prevLetter = null;
			if ((i-1) > 0) {
				prevLetter = mDataList.get(i-1).getFirstLetter();
			}
			
			if (!curLetter.equals(prevLetter)) {
				mSectionPos.put(j, i);
				mSectionBuilder.append(curLetter);
				j++;
			}
		}
		
		mSectionSize = mSectionPos.size();
		mPositions = new int[mSectionPos.size()];
		for (int i = 0; i < mSectionSize; i++) {
			mPositions[i] = mSectionPos.get(i);
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}

	@Override
	public int getPositionForSection(int sectionIndex) {
		if (sectionIndex < 0 || sectionIndex >= mSectionSize) {
            return -1;
        }

        return mPositions[sectionIndex];
	}

	@Override
	public int getSectionForPosition(int position) {
		if (position < 0 || position >= mCount) {
            return -1;
        }

        int index = Arrays.binarySearch(mPositions, position);

        return index >= 0 ? index : -index - 2;
	}
	
	public int getPositonForLetter(String letter) {
		int index = mSectionBuilder.indexOf(letter);
		return getPositionForSection(index);
	}
}
