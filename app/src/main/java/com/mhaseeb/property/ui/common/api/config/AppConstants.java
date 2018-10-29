package com.androiddev.muhammadhaseeb.mypti.helpers;

public class AppConstants {

    //Web URLS
//    public static final String BASE_URL = "http://10.0.1.200:9009"; // Hashim Bhai PC
    //    public static final String BASE_URL = "http://10.0.7.206:92";   //Sheriyar PC
//    public static final String BASE_URL = "http://212.119.87.23:94";   //Public IP

        public static final String BASE_URL = "http://212.119.87.27";   //Public IP
    public static final String URL_LOGIN = "/ptiapi/pti/{username}/{password}/GetUSerID";
    public static final String URL_REGISTER_MEMBER = "/ptiapi/pti/RegisterMember";
    public static final String URL_REGISTER_VOLUNTEER = "/ptiapi/pti/RegisterVolunteer";
    public static final String URL_GET_CITIES = "/ptiapi/pti/{countryCode}/GetCities";
    public static final String URL_GET_REGISTRATION_META_DATA = "/ptiapi/pti/GetRegistrationTerritoryModel";
    public static final String URL_GET_MASTER_DATA = "/ptiapi/pti/GetMasterDataModel";
    public static final String URL_AUTHENTICATE_MEMBERSHIP_ID = "/ptiapi/pti/{MemberID}/GetMembersExpiry";
    public static final String URL_SPEAKUP_MESSAGE = "/ptiapi/pti/SpeakupMessage";
    public static final String URL_GET_PUBLIC_POLLS = "/ptiapi/pti/GetPublicPoll";
    public static final String URL_GET_SUBMIT_PUBLIC_POLLS = "/ptiapi/pti/PublicPoll";
    public static final String URL_GET_PARTY_POLLS = "/ptiapi/pti/GetPartyPoll";
    public static final String URL_GET_SUBMIT_PARTY_POLLS = "/ptiapi/pti/PartyPoll";
    public static final String URL_EXPORT_MEMBERS = "/ptiapi/pti/ExportMemberList";

//    public static final String URL_LOGIN = "/pti/{username}/{password}/GetUSerID";
//    public static final String URL_REGISTER_MEMBER = "/pti/RegisterMember";
//    public static final String URL_REGISTER_VOLUNTEER = "/pti/RegisterVolunteer";
//    public static final String URL_GET_CITIES = "/pti/{countryCode}/GetCities";
//    public static final String URL_GET_REGISTRATION_META_DATA = "/pti/GetRegistrationTerritoryModel";
//    public static final String URL_GET_MASTER_DATA = "/pti/GetMasterDataModel";
//    public static final String URL_AUTHENTICATE_MEMBERSHIP_ID = "/pti/{MemberID}/GetMembersExpiry";
//    public static final String URL_SPEAKUP_MESSAGE = "/pti/SpeakupMessage";
//    public static final String URL_GET_PUBLIC_POLLS = "/pti/GetPublicPoll";
//    public static final String URL_GET_SUBMIT_PUBLIC_POLLS = "/pti/PublicPoll";
//    public static final String URL_GET_PARTY_POLLS = "/pti/GetPartyPoll";
//    public static final String URL_GET_SUBMIT_PARTY_POLLS = "/pti/PartyPoll";
//    public static final String URL_EXPORT_MEMBERS = "/pti/ExportMemberList";

    /**
     * Twitter Constants and Keys
     **/
//    public static final String ACCESS_TOKEN = "955689939026530305-mTMquqh1gxpUFAHX5hlNObAbhhw1UJl";
    //    public static final String ACCESS_TOKEN_SECRET = "GXfSoDQzJY9V6VjPWii7nzuKrrdJfZh5urbjngpinO2c2";
    //    public static final String CONSUMER_KEY = "f4q2OuGypl5BDBeyLFyBJH2Mg";
    //    public static final String CONSUMER_SECRET = "c3PFGq1ZKWZyWC9RbCGNvrLcNUp902n9JkzA97ybrX8ryuTTm0";

    public static final String ACCESS_TOKEN = "956258850453229568-Gi9V24qs4Pl9FNjLtT5t0JG16aH21pS";
    public static final String ACCESS_TOKEN_SECRET = "G8e48spCXQI2Cr4phWp0zedZKo5KNZ3TAsqxaco5gusLJ";
    public static final String CONSUMER_KEY = "RvS3FdfsByL7gujdHNtK5TVXT";
    public static final String CONSUMER_SECRET = "fhc8Lb7xTRA6dbDTnows70TG64Xk89WqJBqbAdtgyTalQ13kG7";
    public static final String twitterAcccountToDisplay = "ImranKhanPTI";


    // App Variables
    public static final String MALE = "Male";
    public static final String FEMALE = "Female";
    public static final String ONLINE = "online";
    public static final String OFFLINE = "offline";
    public static final String MEMBERSHIP_ID = "membershipId";
    public static final String MEMBERSHIP_EXPIRY_DATE = "membershipExpiryDate";


}
