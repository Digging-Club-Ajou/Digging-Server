package server.domain.member.vo;

public enum Gender {

    MALE, FEMALE, UNKNOWN;

    public static Gender getGender(String gender){
        if(gender == "female"){
            return Gender.FEMALE;
        } else if (gender == "male") {
            return Gender.MALE;
        }else{
            return Gender.UNKNOWN;
        }
    }
}
