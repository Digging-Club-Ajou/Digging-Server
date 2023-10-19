package server.domain.member.vo;

public enum Gender {

    MALE, FEMALE, UNKNOWN, Binary;

    public static Gender getGender(String gender){
        if(gender == "female" || gender == "f"){
            return Gender.FEMALE;
        } else if (gender == "male" || gender == "m") {
            return Gender.MALE;
        } else if(gender == "binary" || gender == "b"){
            return Gender.Binary;
        }else{
            return Gender.UNKNOWN;
        }
    }
}
