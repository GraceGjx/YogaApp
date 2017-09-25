package jiaxuan2.yoga.BaseClass;

import java.util.ArrayList;

/**
 * Created by jiaxuan2 on 4/18/17.
 */

public class User{
    private String name;
    private ArrayList<PracticeData> data;

    public User() {}

    public User(String name) {
        this.name = name;
        data = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   public ArrayList<PracticeData> getData() {
       return data;
   }

   public void addData(PracticeData newData) {
       data.add(data.size(), newData);
   }

   public double getCurrentData() {
       if(data.size() > 0) {
           return data.get(data.size() - 1).getPracticeTime();
       }
       else {
           return 0.0;
       }
   }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", data=" + data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        User user = (User) o;

        boolean isDataSame = true;
        if(data.size() != user.data.size()){
            isDataSame = false;
        } else{
            for(int i = 0; i < data.size(); i++){
                if(!data.get(i).equals(user.data.get(i))){
                    isDataSame = false;
                }
            }
        }
        return name.equals(user.name) && isDataSame;
    }

}
