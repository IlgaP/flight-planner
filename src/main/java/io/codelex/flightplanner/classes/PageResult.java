package io.codelex.flightplanner.classes;


import java.util.List;

public class PageResult<T> {

    private int page;
    private int totalItems;
    private List<T> items;

    public PageResult(List<T> items) {
        this.totalItems = items.size();
        this.items = items;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "PageResult{"
                + "page=" + page
                + ", totalItems=" + totalItems
                + ", items=" + items + '}';
    }
}
