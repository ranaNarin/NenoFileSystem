package com.rana.neonfilesystem.models;

public class ElementModel {

    public int _id;
    public String element;
    public int parent_id;
    public int is_dir;
    public String create_time;
    public String modified_time;


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getIs_dir() {
        return is_dir;
    }

    public void setIs_dir(int is_dir) {
        this.is_dir = is_dir;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getModified_time() {
        return modified_time;
    }

    public void setModified_time(String modified_time) {
        this.modified_time = modified_time;
    }
}
