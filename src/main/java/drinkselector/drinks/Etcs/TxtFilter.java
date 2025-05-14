package drinkselector.drinks.Etcs;


import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class TxtFilter {


    private static String [] file_end_name_list={".jpeg",".png",".jpg"};


    private static String [] file_name_black_list={"\\|",",",">","<","&","/","\\\\",":"};

    private static String [] txt_black_list={};


    public static boolean file_end_name_filter(String file_name){


        List<String> names=Arrays.stream(file_end_name_list).filter(x-> {
                if(file_name.endsWith(x)){

                    return false;
                }
                return true;

            }
            ).collect(Collectors.toList());


        if(!names.isEmpty()){

           return false;
        }

        return true;


    }

    public static String get_file_end_name(String file_name){


        for(String end_name:file_end_name_list){

           if(file_name.endsWith(end_name)){


               return end_name;
           }

        }

        throw new RuntimeException();

    }


    public static String file_name_filter(String file_name){


        for(String patter:file_name_black_list){

            file_name=file_name.replaceAll(patter,"");

        }


        return file_name;

    }



    //이 메서드에서 이미지 네임 배열을 넣어서 본문에 표시된 이미지이름을 가져오느걸 바꿔치는것을 만들자. 이미지  로드는 다른 어플리케이션으로 이양
    //또한replace과정에서 세부 로직을 구현을 또해야될듯..ㅇㅇ;
    public static String file_txt_filter(String file_txt,List<String> img_id){


        for(String pattern:txt_black_list){


            file_txt=file_txt.replaceAll(pattern,"");

        }


        /*
        * 이미지 태그를 바꾸는 과정이 들어갈건대
        *
        * 썸내일도 따로 패턴을 만들어서 매칭을 해줘야될듯?
        *
        * */


        return file_txt;

    }

    public static String file_txt_filter(String file_txt){


        for(String pattern:txt_black_list){


            file_txt=file_txt.replaceAll(pattern,"");

        }


        return file_txt;

    }

}
