package com.netpaisa.aepsriseinlib.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.netpaisa.aepsriseinlib.R;

import java.io.Serializable;
import java.util.List;

public class SearchableListDialog extends DialogFragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    private static final String ITEMS = "items";
    private ArrayAdapter listAdapter;
    private ListView _listViewItems;
    private SearchableItem _searchableItem;
    private OnSearchTextChanged _onSearchTextChanged;
    private SearchView _searchView;
    private String _strTitle;
    private String _strPositiveButtonText;
    private DialogInterface.OnClickListener _onClickListener;

    public SearchableListDialog() {
    }

    public static SearchableListDialog newInstance(List items) {
        SearchableListDialog multiSelectExpandableFragment = new SearchableListDialog();
        Bundle args = new Bundle();
        args.putSerializable("items", (Serializable)items);
        multiSelectExpandableFragment.setArguments(args);
        return multiSelectExpandableFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.getDialog().getWindow().setSoftInputMode(2);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(this.getActivity());
        if (null != savedInstanceState) {
            this._searchableItem = (SearchableItem)savedInstanceState.getSerializable("item");
        }

        View rootView = inflater.inflate(R.layout.searchable_list_dialog, (ViewGroup)null);
        this.setData(rootView);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.getActivity());
        alertDialog.setView(rootView);
        String strPositiveButton = this._strPositiveButtonText == null ? "CLOSE" : this._strPositiveButtonText;
        alertDialog.setPositiveButton(strPositiveButton, this._onClickListener);
        String strTitle = this._strTitle == null ? "Select Bank" : this._strTitle;
        alertDialog.setTitle(strTitle);
        AlertDialog dialog = alertDialog.create();
        dialog.getWindow().setSoftInputMode(2);
        return dialog;
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("item", this._searchableItem);
        super.onSaveInstanceState(outState);
    }

    public void setTitle(String strTitle) {
        this._strTitle = strTitle;
    }

    public void setPositiveButton(String strPositiveButtonText) {
        this._strPositiveButtonText = strPositiveButtonText;
    }

    public void setPositiveButton(String strPositiveButtonText, DialogInterface.OnClickListener onClickListener) {
        this._strPositiveButtonText = strPositiveButtonText;
        this._onClickListener = onClickListener;
    }

    public void setOnSearchableItemClickListener(SearchableItem searchableItem) {
        this._searchableItem = searchableItem;
    }

    public void setOnSearchTextChangedListener(OnSearchTextChanged onSearchTextChanged) {
        this._onSearchTextChanged = onSearchTextChanged;
    }

    @SuppressLint("WrongConstant")
    private void setData(View rootView) {
         SearchManager searchManager = (SearchManager)this.getActivity().getSystemService("search");
        this._searchView = (SearchView)rootView.findViewById(R.id.search);
        this._searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getActivity().getComponentName()));
        this._searchView.setIconifiedByDefault(false);
        this._searchView.setOnQueryTextListener(this);
        this._searchView.setOnCloseListener(this);
        this._searchView.setFocusable(false);
        this._searchView.clearFocus();
        InputMethodManager mgr = (InputMethodManager)this.getActivity().getSystemService("input_method");
        mgr.hideSoftInputFromWindow(this._searchView.getWindowToken(), 0);
        List items = (List)this.getArguments().getSerializable("items");
        this._listViewItems = (ListView)rootView.findViewById(R.id.listItems);
       // this.listAdapter = new ArrayAdapter(this.getActivity(), 17367043, items);
        this.listAdapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_list_item_1, items);
        this._listViewItems.setAdapter(this.listAdapter);
        this._listViewItems.setTextFilterEnabled(true);
        this._listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 SearchableListDialog.this._searchableItem.onSearchableItemClicked(SearchableListDialog.this.listAdapter.getItem(position), position);
                 SearchableListDialog.this.getDialog().dismiss();
            }
        });
        this._searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    SearchableListDialog.this.showInputMethod(v.findFocus());
                }

            }
        });
    }

    @SuppressLint("WrongConstant")
    private void showInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager)this.getActivity().getSystemService("input_method");
        if (imm != null) {
            imm.showSoftInput(view, 0);
        }

    }

    public boolean onClose() {
        return false;
    }

    public boolean onQueryTextSubmit(String s) {
        this._searchView.clearFocus();
        return true;
    }

    public boolean onQueryTextChange(String s) {
        if (TextUtils.isEmpty(s)) {
            ((ArrayAdapter)this._listViewItems.getAdapter()).getFilter().filter((CharSequence)null);
        } else {
            ((ArrayAdapter)this._listViewItems.getAdapter()).getFilter().filter(s);
        }

        if (null != this._onSearchTextChanged) {
            this._onSearchTextChanged.onSearchTextChanged(s);
        }

        return true;
    }

    public interface OnSearchTextChanged {
        void onSearchTextChanged(String var1);
    }

    public interface SearchableItem<T> extends Serializable {
        void onSearchableItemClicked(T var1, int var2);
    }
}
