package drinkselector.drinks.Etcs.Enums;

public enum StateEnum {


    Success_Normally("작업 처리가 성공적입니다"),

    Fail_Normally("작업 처리가 실패했습니다."),

    Fail_Need_Login("로그인이 필요합니다"),
    Fail_Exist_Id("이미 존재하는 아이디입니다."),

    Fail_No_Exist_Member("일치하는 회원이 없습니다"),

    Fail_Not_Allowed_Member("권한이 부족합니다.");
    StateEnum(String states) {
        this.states = states;
    }

    private String states;


    public String getStates() {
        return states;
    }
}
