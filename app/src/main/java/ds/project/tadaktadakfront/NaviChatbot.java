package ds.project.tadaktadakfront;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ds.project.tadaktadakfront.chatbot.ChatbotActivity;

public class NaviChatbot extends Fragment {
    private View view;
    private Button btn_chatbotstart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_navi_chat_bot,container,false);

        btn_chatbotstart = (Button) view.findViewById(R.id.btn_chatbotstart);

        btn_chatbotstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChatbotActivity.class); //fragment라서 activity intent와는 다른 방식
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                Toast toast = Toast.makeText(getActivity(), "챗봇에게 먼저 인사해보세요.",Toast.LENGTH_SHORT);
                toast.show();            }
        });
        return view;
    }
}
