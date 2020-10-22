package com.app.alertamedicamento.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.alertamedicamento.util.formatadores.CEP;
import com.app.alertamedicamento.util.formatadores.Telefone;
import com.app.alertamedicamento.util.texto.Texto;
import com.app.alertamedicamento.util.validadores.CNPJ;
import com.app.alertamedicamento.util.validadores.CPF;
import com.app.alertamedicamento.util.validadores.Email;

import java.sql.Date;
import java.sql.Time;

/**
 * @author Desconhecido
 * @company Desconhecido
 */
public class FocusControl implements View.OnFocusChangeListener {

    private Context context = null;
    private EditText editText = null;
    private TypeControl control = null;

    public FocusControl(Context context, EditText editText, TypeControl control) {
        this.context = context;
        this.editText = editText;
        this.control = control;
    }

    public static FocusControl getInstance(Context context, EditText editText, TypeControl control) {
        return new FocusControl(context, editText, control);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        try {
            switch (this.control) {
                case CPFCNPJ: {
                    String value = Texto.manterNumeros(editText.getText().toString().trim());

                    if (hasFocus) {
                        editText.setText(value);
                        editText.setSelection(value.length() == 0 ? 0 : value.length());
                        editText.setTextColor(Color.BLACK);
                    } else {
                        if (value.length() > 11) {
                            if (CNPJ.isValido(value)) {
                                editText.setText(com.app.alertamedicamento.util.formatadores.CNPJ.formatar(value));
                                editText.setTextColor(Color.BLACK);
                            } else {
                                editText.setTextColor(Color.RED);
                                Toast.makeText(context, "CNPJ informado não é válido!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (CPF.isValido(value)) {
                                editText.setText(com.app.alertamedicamento.util.formatadores.CPF.formatar(value));
                                editText.setTextColor(Color.BLACK);
                            } else {
                                editText.setTextColor(Color.RED);
                                Toast.makeText(context, "CPF informado não é válido!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    break;
                }

                case DATA: {
                    String value = editText.getText().toString().trim();

                    if (hasFocus) {
                        editText.setText(value);
                        editText.setSelection(value.length() == 0 ? 0 : value.length());
                        editText.setTextColor(Color.BLACK);
                    } else {
                        if (value.length() == 10) {
                            Date date = Funcoes.strToDateDDMMYYYY(value);

                            if (date != null) {
                                editText.setText(value);
                                editText.setTextColor(Color.BLACK);
                            } else {
                                editText.setTextColor(Color.RED);
                                Toast.makeText(context, "DATA informada não é válido!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    break;
                }

                case HORA: {
                    String value = editText.getText().toString().trim();

                    if (hasFocus) {
                        editText.setText(value);
                        editText.setSelection(value.length() == 0 ? 0 : value.length());
                        editText.setTextColor(Color.BLACK);
                    } else {
                        if (value.length() == 10) {
                            Time time = Funcoes.strTimetoTime(value);

                            if (time != null) {
                                editText.setText(value);
                                editText.setTextColor(Color.BLACK);
                            } else {
                                editText.setTextColor(Color.RED);
                                Toast.makeText(context, "HORARIO informado nao e valido!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    break;
                }

                case CEP: {
                    String value = Texto.manterNumeros(editText.getText().toString().trim());

                    if (hasFocus) {
                        editText.setText(value);
                        editText.setSelection(value.length() == 0 ? 0 : value.length());
                        editText.setTextColor(Color.BLACK);
                    } else {

                        editText.setText(CEP.formatar(value, true));
                        editText.setTextColor(Color.BLACK);

                    }

                    break;
                }

                case FONE: {
                    String value = Texto.manterNumeros(editText.getText().toString().trim());

                    if (hasFocus) {
                        editText.setText(value);
                        editText.setSelection(value.length() == 0 ? 0 : value.length());
                        editText.setTextColor(Color.BLACK);
                    } else {

                        editText.setText(Telefone.formatar(value));
                        editText.setTextColor(Color.BLACK);

                    }

                    break;
                }

                case EMAIL: {
                    String value = editText.getText().toString().trim();

                    if (hasFocus) {
                        editText.setText(value);
                        editText.setSelection(value.length() == 0 ? 0 : value.length());
                        editText.setTextColor(Color.BLACK);
                    } else {
                        if (Email.isValido(value)) {
                            editText.setText(value);
                            editText.setTextColor(Color.BLACK);
                        } else {
                            editText.setTextColor(Color.RED);
                            Toast.makeText(context, "e-mail informado não é válido!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    break;
                }

                case OBRIGATORIO: {
                    String value = editText.getText().toString().trim();

                    if (hasFocus) {
                        editText.setText(value);
                        editText.setSelection(value.length() == 0 ? 0 : value.length());
                        editText.setTextColor(Color.BLACK);
                    } else {
                        if (!value.equals("")) {
                            editText.setText(value);
                            editText.setTextColor(Color.BLACK);
                        } else {
                            editText.setTextColor(Color.RED);
                            Toast.makeText(context, "Campo obrigatório não informado!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    break;
                }

                default: {
                    String value = editText.getText().toString().trim();

                    if (hasFocus) {
                        editText.setText(value);
                        editText.setSelection(value.length() == 0 ? 0 : value.length());
                        editText.setTextColor(Color.BLACK);
                    } else {
                        editText.setText(value);
                        editText.setTextColor(Color.BLACK);
                    }

                    break;
                }
            }

        } catch (Exception e) {
            if (!editText.getText().toString().trim().equals("")) {
                Toast.makeText(context, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                editText.setTextColor(Color.RED);
            }
        }

    }

    public enum TypeControl {
        TEXTUAL, CPFCNPJ, FONE, CEP, OBRIGATORIO, EMAIL, DATA, HORA;
    }

}
