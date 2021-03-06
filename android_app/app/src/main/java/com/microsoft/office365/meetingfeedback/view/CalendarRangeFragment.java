/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.view;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.microsoft.office365.meetingfeedback.BaseFragment;
import com.microsoft.office365.meetingfeedback.CalendarActivity;
import com.microsoft.office365.meetingfeedback.PageSettable;
import com.microsoft.office365.meetingfeedback.R;
import com.microsoft.office365.meetingfeedback.model.DataStore;
import com.microsoft.office365.meetingfeedback.model.meeting.EventGroup;
import com.microsoft.office365.meetingfeedback.util.FormatUtil;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class CalendarRangeFragment extends BaseFragment {

    public static final String PAGE = "PAGE";
    @Inject
    public DataStore mDataStore;

    private TextView mRangeOne;
    private TextView mRangeTwo;
    private TextView mRangeThree;
    private TextView mRangeFour;
    private List<TextView> mRanges;
    private int mActivePage;
    private PageSettable mPageSettable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_range_display, container, false);
        mRangeOne = (TextView) view.findViewById(R.id.range_one);
        mRangeTwo = (TextView) view.findViewById(R.id.range_two);
        mRangeThree = (TextView) view.findViewById(R.id.range_three);
        mRangeFour = (TextView) view.findViewById(R.id.range_four);
        mRanges = Arrays.asList(mRangeOne, mRangeTwo, mRangeThree, mRangeFour);
        if (savedInstanceState != null) {
            mActivePage = savedInstanceState.getInt(PAGE, 0);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PAGE, mActivePage);
    }

    @Override
    public void onResume() {
        super.onResume();
        setup();
    }

    public void setup() {
        List<EventGroup> eventGroups = mDataStore.getEventGroups();
        for (int i = 0; i < eventGroups.size(); i++) {
            EventGroup eventGroup = eventGroups.get(i);
            TextView range = mRanges.get(i);
            range.setText(FormatUtil.formatDateCompact(eventGroup.mDateRange.mEnd.getTime()));
            final int page = i;
            range.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setActivePage(page);
                    mPageSettable.onPageSet(page);
                }
            });
        }
        setActivePage(mActivePage);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPageSettable = (PageSettable)context;
    }

    public void setActivePage(int i) {
        mActivePage = i;
        for (TextView range : mRanges) {
            range.setAlpha(0.5f);
            range.setTypeface(Typeface.DEFAULT);
            range.setPaintFlags(range.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
        }
        TextView textView = mRanges.get(i);
        textView.setAlpha(1.0f);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

}
