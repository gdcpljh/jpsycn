/**
 * Copyright 2012 
 * 
 * Nicolas Desjardins  
 * https://github.com/mrKlar
 * 
 * Facilite solutions
 * http://www.facilitesolutions.com/
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.jpsycn.jixiao.gridview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.TextView;
import ca.laplanete.mobile.pageddragdropgrid.PagedDragDropGrid;
import ca.laplanete.mobile.pageddragdropgrid.PagedDragDropGridAdapter;

import com.jpsycn.jixiao.R;
import com.jpsycn.jixiao.view.CircledImageView;

public class ExamplePagedDragDropGridAdapter implements PagedDragDropGridAdapter {

	private Context context;
	private PagedDragDropGrid gridview;
	
	List<Page> pages = new ArrayList<Page>();
	
	public ExamplePagedDragDropGridAdapter(Context context, PagedDragDropGrid gridview) {
		super();
		this.context = context;
		this.gridview = gridview;
		
		Page page1 = new Page();
		List<Item> items = new ArrayList<Item>();
		items.add(new Item("604", "赵长生", R.drawable.leader_604));
		items.add(new Item("560", "张丽", R.drawable.leader_560));
		items.add(new Item("847", "石磊", R.drawable.leader_847));
		items.add(new Item("427", "王向峰", R.drawable.leader_427));
		items.add(new Item("736", "李海红", R.drawable.leader_736));
		items.add(new Item("210", "李要强", R.drawable.leader_210));
		items.add(new Item("879", "李玉霞", R.drawable.leader_879));
		page1.setItems(items);
		pages.add(page1);
		
		
	}

	@Override
	public int pageCount() {
		return pages.size();
	}

	private List<Item> itemsInPage(int page) {
		if (pages.size() > page) {
			return pages.get(page).getItems();
		}	
		return Collections.emptyList();
	}

    @Override
	public View view(int page, int index) {
    	Item item = getItem(page, index);
    	View view = LayoutInflater.from(context).inflate(R.layout.grid_item, null);
    	CircledImageView icon=(CircledImageView) view.findViewById(R.id.imageView1);
    	icon.setImageResource(item.getDrawable());
    	
//		//icon.setPadding(15, 15, 15, 15);
    	
	TextView label =(TextView)view.findViewById(R.id.textView1);
	label.setText(item.getName());
	
	TextView label2 =(TextView)view.findViewById(R.id.textView2);
	label2.setText(item.getId());
	
//		// only set selector on every other page for demo purposes
//		// if you do not wish to use the selector functionality, simply disregard this code
		if(page % 2 == 0) {
    		setViewBackground(view);
    		view.setClickable(true);
    		view.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return gridview.onLongClick(v);
                }
    		});
		}
		return view;
	}

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setViewBackground(View view) {
        if (android.os.Build.VERSION.SDK_INT >= 16) {
        	view.setBackground(context.getResources().getDrawable(R.drawable.list_selector_holo_light));
        }
    }

	private Item getItem(int page, int index) {
		List<Item> items = itemsInPage(page);
		return items.get(index);
	}

	@Override
	public int rowCount() {
		return AUTOMATIC;
	}

	@Override
	public int columnCount() {
		return AUTOMATIC;
	}

	@Override
	public int itemCountInPage(int page) {
		return itemsInPage(page).size();
	}

	public void printLayout() {
		int i=0;
		for (Page page : pages) {
			Log.d("Page", Integer.toString(i++));
			
			for (Item item : page.getItems()) {
				Log.d("Item", item.getId());
			}
		}
	}

	private Page getPage(int pageIndex) {
		return pages.get(pageIndex);
	}

	@Override
	public void swapItems(int pageIndex, int itemIndexA, int itemIndexB) {
		getPage(pageIndex).swapItems(itemIndexA, itemIndexB);
	}

	@Override
	public void moveItemToPreviousPage(int pageIndex, int itemIndex) {
		int leftPageIndex = pageIndex-1;
		if (leftPageIndex >= 0) {
			Page startpage = getPage(pageIndex);
			Page landingPage = getPage(leftPageIndex);
			
			Item item = startpage.removeItem(itemIndex);
			landingPage.addItem(item);	
		}	
	}

	@Override
	public void moveItemToNextPage(int pageIndex, int itemIndex) {
		int rightPageIndex = pageIndex+1;
		if (rightPageIndex < pageCount()) {
			Page startpage = getPage(pageIndex);
			Page landingPage = getPage(rightPageIndex);
			
			Item item = startpage.removeItem(itemIndex);
			landingPage.addItem(item);			
		}	
	}

	@Override
	public void deleteItem(int pageIndex, int itemIndex) {
		getPage(pageIndex).deleteItem(itemIndex);
	}

    @Override
    public int deleteDropZoneLocation() {        
        return BOTTOM;
    }

    @Override
    public boolean showRemoveDropZone() {
        return true;
    }
	
}
