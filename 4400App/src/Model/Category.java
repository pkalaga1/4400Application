package Model;

import Links.EditCategoryLink;

public class Category {

	private String name;
	private int numOfAttr;
	private EditCategoryLink editCategoryHyperLink;

	public Category(String n) {
		name = n;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return name;
	}

	public void setName(String s) { name = s; }

	public void setNumOfAttr(int n) { numOfAttr = n; }

	public int getNumOfAttr() { return numOfAttr; }

	public EditCategoryLink getEditCategoryHyperLink() { return editCategoryHyperLink; }

	public void setEditCategoryHyperLink(EditCategoryLink c) { editCategoryHyperLink = c; }
}
