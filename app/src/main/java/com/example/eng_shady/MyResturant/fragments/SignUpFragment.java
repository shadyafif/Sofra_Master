package com.example.eng_shady.MyResturant.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.activities.ClientActivity;
import com.example.eng_shady.MyResturant.api.Api;
import com.example.eng_shady.MyResturant.api.RetrofitClient;

import com.example.eng_shady.MyResturant.models.Client.Register.ClientRegister;
import com.example.eng_shady.MyResturant.models.General.Cities;
import com.example.eng_shady.MyResturant.models.General.DatumCity;
import com.example.eng_shady.MyResturant.models.General.DatumReqion;
import com.example.eng_shady.MyResturant.models.General.Region;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.eng_shady.MyResturant.adapters.Helper.Replace;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {
    EditText TxtRegisterName, TxtRegisterEmail, TxtRegisterPhone,
            TxtRegisterHome, TxtRegisterPass, TxtRegisterPassConfirm;

    TextView txtRegisterCountry, txtRegisterown;

    Spinner spRegisterCountry, spRegistertown;

    Button btnCliRegister;

    String txtid;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("حساب جديد");

        TxtRegisterName = v.findViewById(R.id.TxtRegisterName);
        TxtRegisterEmail = v.findViewById(R.id.TxtRegisterEmail);
        TxtRegisterPhone = v.findViewById(R.id.TxtRegisterPhone);
        TxtRegisterHome = v.findViewById(R.id.TxtRegisterHome);
        TxtRegisterPass = v.findViewById(R.id.TxtRegisterPass);
        TxtRegisterPassConfirm = v.findViewById(R.id.TxtRegisterPassConfirm);


        spRegisterCountry = v.findViewById(R.id.spRegisterCountry);
        spRegistertown = v.findViewById(R.id.spRegistertown);

        btnCliRegister = v.findViewById(R.id.btnCliRegister);
        btnCliRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });
        GetCities();

        return v;
    }

    private void GetCities() {
        final Api dataservices = RetrofitClient.getInstance().getApi();
        Call<Cities> call = dataservices.GetCities();
        call.enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {

                Cities CT = response.body();
                List<DatumCity> LisOfCities = CT.getData().getData();

                List<String> listSpinner = new ArrayList<String>();
                listSpinner.add("اختار المدينة");
                final List<Integer> listIds = new ArrayList<>();

                for (int i = 0; i < LisOfCities.size(); i++) {
                    listSpinner.add(LisOfCities.get(i).getName());
                    listIds.add(LisOfCities.get(i).getId());
                }

                final int[] id = {-1};

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spRegisterCountry.setAdapter(adapter);
                spRegisterCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long i) {

                        if (position != 0) {
                            id[0] = listIds.get(position - 1);
                            getListRegions(id[0]);

//                            txtRegisterCountry.setText(spRegisterCountry.getSelectedItem().toString());
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {

            }
        });
    }

    private void getListRegions(int city_id) {
        final Api dataservices2 = RetrofitClient.getInstance().getApi();
        Call<Region> call = dataservices2.getListRegion(city_id);
        call.enqueue(new Callback<Region>() {
            @Override
            public void onResponse(Call<Region> call, Response<Region> response) {
                Region re = response.body();
                final List<DatumReqion> listofcity = re.getData().getData();
                final List<String> listSpinner = new ArrayList<String>();
                final List<Integer> listSpinnerIds = new ArrayList<>();

                for (int i = 0; i < listofcity.size(); i++) {
                    listSpinner.add(listofcity.get(i).getName());
                    listSpinnerIds.add(listofcity.get(i).getId());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                spRegistertown.setAdapter(adapter);
                spRegistertown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long L) {
                        Log.i("selectedItem", listSpinner.get(position));

                        int id = listSpinnerIds.get(position);
                        txtid = Integer.toString(id);


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Region> call, Throwable t) {

            }
        });

    }


    private void SignUp() {


        String name = TxtRegisterName.getText().toString().trim();
        String email = TxtRegisterEmail.getText().toString().trim();
        String phone = TxtRegisterPhone.getText().toString().trim();
        String password = TxtRegisterPass.getText().toString().trim();
        String confirmpass = TxtRegisterPassConfirm.getText().toString().trim();
        String Address = TxtRegisterHome.getText().toString().trim();
        String CityId = txtid.trim();

        if (name.isEmpty()) {
            TxtRegisterName.setError("هذا الحقل مطلوب");
            TxtRegisterName.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            TxtRegisterPhone.setError("name is required");
            TxtRegisterPhone.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            TxtRegisterEmail.setError("هذا الحقل مطلوب");
            TxtRegisterEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            TxtRegisterEmail.setError("الرجاء إدخال عنوان بريد إلكترونى صحيح");
            TxtRegisterEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            TxtRegisterPass.setError("هذا الحقل مطلوب");
            TxtRegisterPass.requestFocus();
            return;
        }

        if (Address.isEmpty()) {
            TxtRegisterHome.setError("هذا الحقل مطلوب");
            TxtRegisterHome.requestFocus();
            return;
        }

        Call<ClientRegister> call = RetrofitClient.getInstance().getApi().
                creatUser(name, email, password, confirmpass, phone, Address, txtid);

        call.enqueue(new Callback<ClientRegister>() {
            @Override
            public void onResponse(Call<ClientRegister> call, Response<ClientRegister> response) {
                ClientRegister CR = response.body();
                if (CR.getStatus() != 0) {
                    ClientActivity ss = (ClientActivity) getActivity();

                    LoginFragment login = new LoginFragment();
                    Replace(login, R.id.FragmentLoad, ss.getSupportFragmentManager().beginTransaction());


                }

            }

            @Override
            public void onFailure(Call<ClientRegister> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
