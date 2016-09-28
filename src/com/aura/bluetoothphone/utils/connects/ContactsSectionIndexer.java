package com.aura.bluetoothphone.utils.connects;

import java.util.Arrays;

import android.text.TextUtils;
import android.widget.SectionIndexer;


/**
 * @ClassName: ContactsSectionIndexer
 * @Description: TODO
 * @author: steven zhang
 * @date: Sep 27, 2016 2:32:15 PM
 */
public class ContactsSectionIndexer implements SectionIndexer {

    private String[] mSections;
    private int[] mPositions;
    private int mCount;
    private static final String BLANK_HEADER_STRING = " ";

    /**
     * Constructor.
     *
     * @param sections a non-null array
     * @param counts a non-null array of the same size as <code>sections</code>
     */
    public ContactsSectionIndexer(String[] sections, int[] counts) {
        if (sections == null || counts == null) {
            throw new NullPointerException();
        }

        if (sections.length != counts.length) {
            throw new IllegalArgumentException(
                    "The sections and counts arrays must have the same length");
        }


        this.mSections = sections;
        mPositions = new int[counts.length];
        int position = 0;
        for (int i = 0; i < counts.length; i++) {
            if (TextUtils.isEmpty(mSections[i])) {
                mSections[i] = BLANK_HEADER_STRING;
            } else if (!mSections[i].equals(BLANK_HEADER_STRING)) {
                mSections[i] = mSections[i].trim();
            }

            mPositions[i] = position;
            position += counts[i];
        }
        mCount = position;
    }

    public Object[] getSections() {
        return mSections;
    }

    public int getPositionForSection(int section) {
        if (section < 0 || section >= mSections.length) {
            return -1;
        }

        return mPositions[section];
    }

    public int getSectionForPosition(int position) {
        if (position < 0 || position >= mCount) {
            return -1;
        }

        int index = Arrays.binarySearch(mPositions, position);

        /*
         * Consider this example: section positions are 0, 3, 5; the supplied
         * position is 4. The section corresponding to position 4 starts at
         * position 3, so the expected return value is 1. Binary search will not
         * find 4 in the array and thus will return -insertPosition-1, i.e. -3.
         * To get from that number to the expected value of 1 we need to negate
         * and subtract 2.
         */
        return index >= 0 ? index : -index - 2;
    }
    
    public int getPositionForSections(String s) {
    	int position = -1;
    	for (int i = 0; i < mSections.length; i++) {
    		if (mSections[i].equals(s)) {
    			position = getPositionForSection(i);
    			break;
    		}
		}
    	
    	return position;
    }

}