package ds.project.tadaktadakfront;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ds.project.tadaktadakfront.chatbot.ChatbotActivity;

public class setResultActivity extends AppCompatActivity {

    // 챗봇 연결 버튼
    Button askChatbot;
    // 회사 이름 textView
    TextView companyName;
    // 계약서 종류 나오는 textView
    TextView analysisResult;
    // 계약서 이유 나오는 textView
    TextView analysisReason;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_result);

        //버튼 누르면 챗봇으로 이동
        askChatbot = (Button) findViewById(R.id.askChatbot);
        askChatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatbotActivity.class);
                startActivity(intent);
            }
        });

        Intent result = getIntent();
        // set Image에서 넘어온 회사명 적용하기
        companyName = (TextView) findViewById(R.id.company_name);
        String resultName = result.getStringExtra("resultEname");
        companyName.setText(resultName);

        // 계약서 종류 적용
        analysisResult = (TextView)findViewById(R.id.analysis_result);
        analysisReason = (TextView)findViewById(R.id.reason);

        // setImage에서 값 판별값 받아오기
        int resultType =  result.getIntExtra("resultType",0);
        System.out.println("계약서 종류: "+resultType);
        //analysisResult.setText(resultType);

        if(resultType == 100){
            analysisResult.setText("청소년 근로 계약서");
            analysisReason.setText(getString(R.string.underage));
        }
        // 결과가 없을경우
        else if(resultType == 0){
            analysisResult.setText("판별이 어렵습니다.");
            analysisReason.setText("글자가 잘 보이는 이미지를 이용해주세요.");
        }

    }
}