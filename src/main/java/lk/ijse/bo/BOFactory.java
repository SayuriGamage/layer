package lk.ijse.bo;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getBoFactory(){
        if(boFactory==null){
            boFactory=new BOFactory();
        }
        return boFactory;
    }

    public SuperBo getBO(BOTypes boTypes){
        switch (boTypes){

            case Employee :
                return  new Employeeboimpl();
        }
        return null;
    }
}
