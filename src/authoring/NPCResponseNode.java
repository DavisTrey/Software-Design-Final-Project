package authoring;

import java.util.ArrayList;
import java.util.List;

public class NPCResponseNode {
	public String myString;
	public List<UserQueryNode> myChildren;
	public NPCResponseNode(String s){
		myString = s;
		myChildren = new ArrayList<UserQueryNode>();
	}
	public void setString(String s){
		myString = s;
	}
	public void addChild(UserQueryNode n){
		myChildren.add(n);
	}
	public void removeChild(UserQueryNode n){
		for(UserQueryNode nn: myChildren){
			if(n==nn)
				myChildren.remove(nn);
		}
	}
	
	public String getString(){
		return myString;
	}
	public List<UserQueryNode> getChildren(){
		return myChildren;
	}
}
