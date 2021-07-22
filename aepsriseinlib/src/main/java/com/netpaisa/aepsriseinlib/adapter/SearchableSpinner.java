package com.netpaisa.aepsriseinlib.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.netpaisa.aepsriseinlib.R;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("AppCompatCustomView")
public class SearchableSpinner extends Spinner implements View.OnTouchListener, SearchableListDialog.SearchableItem {

    public static final int NO_ITEM_SELECTED = -1;
    String selectedItem;
    private Context _context;
    private List _items;
    private SearchableListDialog _searchableListDialog;
    private boolean _isDirty;
    private ArrayAdapter _arrayAdapter;
    private String _strHintText;
    private boolean _isFromInit;

    public SearchableSpinner(Context context) {
        super(context);
        this._context = context;
        this.init();
    }

    public SearchableSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this._context = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SearchableSpinner);
        int N = a.getIndexCount();

        for(int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.SearchableSpinner_hintText) {
                this._strHintText = a.getString(attr);
            }
        }

        a.recycle();
        this.init();
    }

    public SearchableSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this._context = context;
        this.init();
    }

    private void init() {
        this._items = new ArrayList();
        this._searchableListDialog = SearchableListDialog.newInstance(this._items);
        this._searchableListDialog.setOnSearchableItemClickListener(this);
        this.setOnTouchListener(this);
        this._arrayAdapter = (ArrayAdapter)this.getAdapter();
        if (!TextUtils.isEmpty(this._strHintText)) {
           // ArrayAdapter arrayAdapter = new ArrayAdapter(this._context, 17367043, new String[]{this._strHintText});
            ArrayAdapter arrayAdapter = new ArrayAdapter(this._context, android.R.layout.simple_list_item_1, new String[]{this._strHintText});
            this._isFromInit = true;
            this.setAdapter((SpinnerAdapter)arrayAdapter);
        }
    }


    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == 1 && null != this._arrayAdapter) {
            this._items.clear();

            for(int i = 0; i < this._arrayAdapter.getCount(); ++i) {
                this._items.add(this._arrayAdapter.getItem(i));
            }

            this._searchableListDialog.show(this.scanForActivity(this._context).getFragmentManager(), "TAG");
        }

        return true;
    }

    public void setAdapter(SpinnerAdapter adapter) {
        if (!this._isFromInit) {
            this._arrayAdapter = (ArrayAdapter)adapter;
            if (!TextUtils.isEmpty(this._strHintText) && !this._isDirty) {
                //ArrayAdapter arrayAdapter = new ArrayAdapter(this._context, 17367043, new String[]{this._strHintText});
                ArrayAdapter arrayAdapter = new ArrayAdapter(this._context, android.R.layout.simple_list_item_1, new String[]{this._strHintText});
                super.setAdapter(arrayAdapter);
            } else {
                super.setAdapter(adapter);
            }
        } else {
            this._isFromInit = false;
            super.setAdapter(adapter);
        }

    }

    public void onSearchableItemClicked(Object item, int position) {
        this.setSelection(this._items.indexOf(item));
        if (!this._isDirty) {
            this._isDirty = true;
            this.setAdapter((SpinnerAdapter)this._arrayAdapter);
            this.setSelection(this._items.indexOf(item));
        }

        this.selectedItem = this.getItemAtPosition(position).toString();
    }

    private Activity scanForActivity(Context cont) {
        if (cont == null) {
            return null;
        } else if (cont instanceof Activity) {
            return (Activity)cont;
        } else {
            return cont instanceof ContextWrapper ? this.scanForActivity(((ContextWrapper)cont).getBaseContext()) : null;
        }
    }

    public int getSelectedItemPosition() {
        return !TextUtils.isEmpty(this._strHintText) && !this._isDirty ? -1 : super.getSelectedItemPosition();
    }

    public Object getSelectedItem() {
        return !TextUtils.isEmpty(this._strHintText) && !this._isDirty ? null : super.getSelectedItem();
    }
}
