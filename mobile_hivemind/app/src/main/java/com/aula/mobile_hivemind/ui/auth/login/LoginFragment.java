package com.aula.mobile_hivemind.ui.auth.login;

import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.aula.mobile_hivemind.R;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment {

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        SignInButton signInButton = view.findViewById(R.id.btnGoogleSignIn);
        signInButton.setElevation(0f); // remove sombra
        signInButton.setStateListAnimator(null); // remove animação de clique com sombra

        TextInputEditText editText = view.findViewById(R.id.editTextNOMEEMP);
        TextInputEditText editText2 = view.findViewById(R.id.editTextPassword);

        editText.post(() -> {
            CharSequence text = editText.getText();
            if (text instanceof Spannable) {
                Selection.setSelection((Spannable) text, 0, Math.min(12, text.length()));
            }
        });

        editText2.post(() -> {
            CharSequence text = editText2.getText();
            if (text instanceof Spannable) {
                Selection.setSelection((Spannable) text, 0, Math.min(12, text.length()));
            }
        });

        return view;
    }
}
