package ds.project.tadaktadakfront;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class setImageActivity extends AppCompatActivity{

        TextRecognizer recognizer =
                TextRecognition.getClient(new KoreanTextRecognizerOptions.Builder().build());

        Handler handler = new Handler();
        public InputImage inputImage;
        String tempText; // 인식된 결과를 넣을 String
        String[] resultText;// 인식된 결과를 parsing후 분석할 String Array

    public ArrayList<String> item = new ArrayList<String>();
    public ArrayList<String> typeitem = new ArrayList<String>();
    public ArrayList<MainList> mainLists = new ArrayList<MainList>();


        //나오는 결과 edittext
        public EditText name;
        public EditText cpName; // 사업자 명
        public EditText enName;  // 근로자 이름
        public EditText number; // 전화번호
        public EditText address; // 사업자 주소
        public EditText start; // 근무 시작일
        public EditText salary; // 돈
        public EditText hours; // 근무시간

        String eName; // editText에 들어갈 상호명
        String rCpName; // editText에 들어갈 사업자
        String rEnName; // editText에 들어갈 근로자 주소
        String rNumber ; // editText에 들어갈 사업자 번호
        String rAddress; // editText에 들어갈 사업자 주소
        String rStart; // editText에 들어갈 근무 시작일
        String rSalary; // editText에 들어갈 돈
        String rHours; // editText에 들어갈 근무시간

        // 근로시간용 체크박스
        CheckBox monCheckbox;
        CheckBox tueCheckbox;
        CheckBox wedCheckbox;
        CheckBox thuCheckbox;
        CheckBox friCheckbox;
        CheckBox satCheckbox;
        CheckBox sunCheckbox;

        // 저장후 다음 액티비티로 넘어가게 해주는 버튼
        Button buttonSave;
        Button buttoncheck;

        // 근로요일을 저장할 변수
        int wDays;

        // 일하는 시간
        int wHours;

        // 판별에 필요한 변수 - 판별값마다 다른 값 준 후 intent로 넘기기
        int contracttype = 0;


    public class MainList{
        public String mName;
        public String mCategory;
        public MainList(String name, String category){
            this.mName = name;
            this.mCategory = category;
        }
        public String getmName() { return mName; }
        public String getmCategory() { return mCategory; }
    }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_set_image);

            name = (EditText)findViewById(R.id.add_edittext_name);
            cpName = (EditText)findViewById(R.id.add_edittext_cpName);  // 사업자명
            enName = (EditText)findViewById(R.id.add_edittext_enName);
            number = (EditText)findViewById(R.id.add_edittext_number); // 사업자 전화번호
            address = (EditText)findViewById(R.id.add_edittext_address); // 사업자 주소
            start = (EditText)findViewById(R.id.add_edittext_start); // 근무 시작일
            salary = (EditText)findViewById(R.id.add_edittext_salary); // 돈
            hours = (EditText)findViewById(R.id.add_edittext_hours);

            //체크박스
            monCheckbox = (CheckBox)findViewById(R.id.monday);
            tueCheckbox =  (CheckBox)findViewById(R.id.tuesday);
            wedCheckbox =  (CheckBox)findViewById(R.id.wednesday);
            thuCheckbox =  (CheckBox)findViewById(R.id.thursday);
            friCheckbox =  (CheckBox)findViewById(R.id.friday);
            satCheckbox =  (CheckBox)findViewById(R.id.saturday);
            sunCheckbox =  (CheckBox)findViewById(R.id.sunday);

            if(monCheckbox.isChecked()){
                wDays++;
            }
            else if (tueCheckbox.isChecked()){
                wDays++;
            }
            else if (wedCheckbox.isChecked()){
                wDays++;
            }
            else if (thuCheckbox.isChecked()){
                wDays++;
            }
            else if (friCheckbox.isChecked()){
                wDays++;
            }
            else if (satCheckbox.isChecked()){
                wDays++;
            }
            else if (sunCheckbox.isChecked()){
                wDays++;
            }


            String currentPhotoPath = this.getIntent().getStringExtra("path");
            final Uri uriSelected = Uri.parse(this.getIntent().getStringExtra("path"));
            File file = new File(currentPhotoPath);
            Uri uri = Uri.fromFile(file);

            Log.v("tag", "successI");

            // 2초후 다음 액티비티로 넘김
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    setImageActivity.this.convertImagetoText(uriSelected);
                    setImageActivity.this.convertImagetoText(uri);
                    Log.v("tag", "successT");
                }
            },1000L);

            buttonSave = (Button)findViewById(R.id.button_save);
            buttoncheck = (Button)findViewById(R.id.button_check);
            buttoncheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent  = new Intent(getApplicationContext(), setResultActivity.class);
                    intent.putExtra("resultEname",name.getText().toString());
                    System.out.println("type "+contracttype);
                    //intent.putExtra("resultType", type);
                    intent.putExtra("resultType", contracttype);
                     startActivity(intent);
                }
            });

            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("number", number.getText().toString());
                intent.putExtra("address", address.getText().toString() );

                startActivity(intent);
                }
            });




        } // end onCreate

        private final void convertImagetoText(Uri imageUri) {
            try {

                inputImage = InputImage.fromFilePath(this, imageUri);

                Task<Text> result =
                        recognizer.process(inputImage)
                                .addOnSuccessListener(new OnSuccessListener<Text>() {
                                    @Override
                                    public void onSuccess(Text visionText) {

                                        tempText = visionText.getText();

                                        System.out.println("성공");
                                        System.out.println(tempText);

                                        splitResult(tempText);

                                        usingNLPAPI Async2 = new usingNLPAPI();
                                        Async2.execute();

//                                        System.out.println("주소:"+rAddress);
//                                        address.setText(rAddress);

                                    }
                                })
                                .addOnFailureListener(
                                        new OnFailureListener() {

                                            public void onFailure(@NonNull Exception e) {
                                                // Task failed with an exception
                                                // ...
                                            }
                                        });
            } catch (Exception e) {

            }

        }// end convertImagetoText

        public void splitResult(String string){

             resultText = string.split("\n");

                for (int i = 0; i< resultText.length; i++) {
                // 정규직==표준근로-황민혜
                if(resultText[i].contains("황민혜")){

                    // '황'포함 시
                    if(resultText[i].startsWith("황")){
                        eName=resultText[i].substring(0,2);
                        name.setText(eName);
                        System.out.println(eName);


                    }


                }

                // 계약직-유희윤
                else if (resultText[i].contains("")) {
                    eName = resultText[i];
                    name.setText(resultText[i]);
                }
                // 청소년-이유나
                // 고용노동부 장관, 취직인허증
                else if (resultText[i].contains("가족")||resultText[i].contains("연소")
                        || resultText[i].contains("취직인허증") || resultText[i].contains("고용노동")) {
                    contracttype = 100;


                }
                /*
                // 외국인
                else if (resultText[i].contains("labor")) {

                }
                */
                // 건설 일용직-문정인
                else if (resultText[i].contains("건설")) {
                    eName= resultText[i];

                }
//


//                if(resultText[i].contains("CS")){
//                    eName = resultText[i];
//                    System.out.println("값"+eName);
//                    name.setText(resultText[i]);
//                }else if(resultText[i].contains("김덕성")){
//                    rCpName = resultText[i];
//                    cpName.setText(resultText[i]);
//                }else if(resultText[i].contains("민혜")){
//                    rEnName = resultText[i];
//                    enName.setText(resultText[i]);
//                }else if(resultText[i].contains("연락처")){
//                    rNumber = resultText[i];
//                    number.setText(resultText[i]);
//                }else if(resultText[i].contains("서울")){
//                    rAddress = resultText[i];
//                    address.setText(resultText[i]);
//                }else if(resultText[i].contains("계약기간")){
//                    rStart = resultText[i];
//                    start.setText(resultText[i]);
//                }else if(resultText[i].contains("임 금")){
//                    rSalary = resultText[i];
//                    salary.setText(resultText[i]);
//                    hours.setText("8"); //일하는 시간
//                }

                System.out.println("나눠진 값"+resultText[i]);
                if(resultText[i].contains("대표자")) // 대표자 이름 찾기
                    cpName.setText(resultText[i]);
                else if(resultText[i].contains("업체명")) // 업체명 찾기
                    name.setText("resultText[i]");
                else if(resultText[i].contains("전화")) // 업체 번호 넣기
                    number.setText(resultText[i]);
                // 판별에 필요한 키워드 찾아서 넣어줌
                else if (resultText[i].contains("가족")||resultText[i].contains("연소")
                        || resultText[i].contains("취직인허증") || resultText[i].contains("고용노동")) {
                    contracttype = 100;
                }

            }

        } // end splitResult

    public class usingNLPAPI extends AsyncTask<String, Void, String> {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override

        protected String doInBackground(String... strings) {

            for (int i = 0; i < resultText.length; i++) {

                String openApiURL = "http://aiopen.etri.re.kr:8000/WiseNLU";
                String accessKey = "b5307d84-0969-4a2c-a53f-f46af682f6d9";
                String analysisCode = "ner";
                // 인식된 텍스트 저장
                String text = resultText[i];
                Gson gson = new Gson();

                Map<String, Object> request = new HashMap<>();
                Map<String, String> argument = new HashMap<>();

                argument.put("analysis_code", analysisCode);
                argument.put("text", text);

                request.put("access_key", accessKey);
                request.put("argument", argument);

                URL url;
                Integer responseCode = null;
                String responBodyJson = null;
                Map<String, Object> responeBody = null;

                try {
                    url = new URL(openApiURL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);

                    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                    wr.write(gson.toJson(request).getBytes("UTF-8"));
                    wr.flush();
                    wr.close();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

                    String line;
                    String page = "";

                    while ((line = reader.readLine()) != null) {
                        page += line;
                    }

                    JsonParser jsonParser = new JsonParser();
                    JsonElement jsonElement = jsonParser.parse(page);


                    String check = jsonElement.getAsJsonObject().get("return_object").getAsJsonObject().get("sentence").getAsJsonArray().get(0).getAsJsonObject().get("NE").getAsJsonArray().toString();
                    System.out.println("에러검사: "+check);
                    if(!check.equals("[]"))
                    {
                        String type = jsonElement.getAsJsonObject().get("return_object").getAsJsonObject().get("sentence").getAsJsonArray().get(0).getAsJsonObject().get("NE").getAsJsonArray().get(0).getAsJsonObject().get("type").toString();

                        //주소 찾는 코드
                        if(type.equals("\"LCP_CAPITALCITY\"") || type.equals("\"LCP_PROVINCE\"") && type.equals("\"LCP_COUNTY\"") && type.equals("\"LCP_CITY\""))
                        {
                            rAddress = text;
                            System.out.println("주소찾음: "+ rAddress); //주소 제대로 나오는지 테스트

                        }
                        // 사업자 이름
                        else if (type.equals("\"PS_NAME\"") && text.matches("대표자")) { // 이름 찾아서 넘겨주기
                            rCpName = text;
                            System.out.println("대표자 이름:"+rCpName);

                        }
                        //근로자 이름 찾음
                        else if (type.equals("\"PS_NAME\"") || text.equals("근로자")){
                            String worker = jsonElement.getAsJsonObject().get("return_object").getAsJsonObject().get("sentence").getAsJsonArray().get(0).getAsJsonObject().get("NE").getAsJsonArray().get(0).getAsJsonObject().get("text").toString();
                            System.out.println("근로자 이름:"+worker);
                            rEnName = worker;

                        }
                        // 시작 날짜 찾음
                        else if (type.equals("\"DT_MONTH\"") || type.equals("\"DT_YEAR\"") && text.equals("부터")){
                            rStart = text;
                        }

                        // 회사 이름 찾음
                        else if(type.equals("\"OG\"") || text.equals("(주)") || text.equals("업체")){
                            String company = jsonElement.getAsJsonObject().get("return_object").getAsJsonObject().get("sentence").getAsJsonArray().get(0).getAsJsonObject().get("NE").getAsJsonArray().get(0).getAsJsonObject().get("text").toString();
                            eName = company;
                            System.out.println("회사이름찾음"+eName);
                        }
                        // 근무시간
                        else if(type.equals("\"TI_DURATION\"")){
                            String hour = jsonElement.getAsJsonObject().get("return_object").getAsJsonObject().get("sentence").getAsJsonArray().get(0).getAsJsonObject().get("NE").getAsJsonArray().get(0).getAsJsonObject().get("text").toString();
                            System.out.println("시간나옴: "+hour);
                            hour = rHours;
                            System.out.println("시간나옴: "+rHours);
                        }

                    }


                   /* responseCode = con.getResponseCode();
                    InputStream is = con.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    StringBuffer sb = new StringBuffer();

                    String inputLine = "";
                    while ((inputLine = br.readLine()) != null) {
                        sb.append(inputLine);
                    }
                    responBodyJson = sb.toString();

                    // http 요청 오류 시 처리
                    if (responseCode != 200) {
                        // 오류 내용 출력
                        System.out.println("[error] " + responBodyJson);
                    }

                    responeBody = gson.fromJson(responBodyJson, Map.class);
                    Integer result = ((Double) responeBody.get("result")).intValue();
                    Map<String, Object> returnObject;
                    List<Map> sentences;

                    // 분석 요청 오류 시 처리
                    if (result != 0) {

                        // 오류 내용 출력
                        System.out.println("[error] " + responeBody.get("result"));
                    }

                    returnObject = (Map<String, Object>) responeBody.get("return_object");
                    sentences = (List<Map>) returnObject.get("sentence");
                    Map<String, NameEntity> nameEntitiesMap = new HashMap<String, NameEntity>();
                    List<NameEntity> nameEntities = null;

                    for (Map<String, Object> sentence : sentences) {

                        List<Map<String, Object>> nameEntityRecognitionResult = (List<Map<String, Object>>) sentence.get("NE");
                        for (Map<String, Object> nameEntityInfo : nameEntityRecognitionResult) {
                            String name = (String) nameEntityInfo.get("text");
                            NameEntity nameEntity = nameEntitiesMap.get(name);
                            System.out.println("개체명 인식 결과" + nameEntityRecognitionResult);
                            if (nameEntity == null) {
                                nameEntity = new NameEntity(name, (String) nameEntityInfo.get("type"), 1);
                                nameEntitiesMap.put(name, nameEntity);

                            } else {
                                nameEntity.count = nameEntity.count + 1;
                            }
                        }
                        System.out.println("");

                    }*/
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        } // end background

        @Override
        protected void onPostExecute(String s) {
            // 인식된 결과가 editText에 출력되도록 설정
            super.onPostExecute(s);

            address.setText(rAddress);
            //cpName.setText(rCpName); // 사업자 명
            enName.setText(rEnName);  // 근로자 이름

            //number; // 전화번호
            start.setText(rStart); // 근무 시작일
//            salary; // 돈
            hours.setText(rHours); // 근무시간
        }
    } // end using NLPAPI

    }
