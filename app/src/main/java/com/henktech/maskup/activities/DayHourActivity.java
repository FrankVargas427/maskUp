package com.henktech.maskup.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dpro.widgets.OnWeekdaysChangeListener;
import com.dpro.widgets.WeekdaysPicker;
import com.henktech.maskup.R;
import com.henktech.maskup.controllers.DayHourController;
import com.henktech.maskup.controllers.NotificationController;
import com.henktech.maskup.controllers.SaveLoadController;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


public class DayHourActivity extends AppCompatActivity {
    final HashMap<Integer, Calendar> calListStart = new HashMap<>();
    Calendar sun, mon, tue, wed, thu, fri, sat;
    LinkedHashMap<Integer, Boolean> map = new LinkedHashMap<>();
    int prev = 0;

    public void initializeDayHour(Context context) {
        // Se insertan los dias de la semana en el widget.
        calListStart.put(1, sun = Calendar.getInstance());
        calListStart.put(2, mon = Calendar.getInstance());
        calListStart.put(3, tue = Calendar.getInstance());
        calListStart.put(4, wed = Calendar.getInstance());
        calListStart.put(5, thu = Calendar.getInstance());
        calListStart.put(6, fri = Calendar.getInstance());
        calListStart.put(7, sat = Calendar.getInstance());

        // Se des-seleccionan todos los dias.
        map.put(Calendar.MONDAY, false);
        map.put(Calendar.TUESDAY, false);
        map.put(Calendar.WEDNESDAY, false);
        map.put(Calendar.THURSDAY, false);
        map.put(Calendar.FRIDAY, false);
        map.put(Calendar.SATURDAY, false);
        map.put(Calendar.SUNDAY, false);

        /*
         En caso de que exista el archivo donde se guardan los dias, se seleccionan
         todos los dias que esten en este archivo.
         */
        HashMap<Integer, Calendar> loadDayHours = (HashMap<Integer, Calendar>)
                SaveLoadController.loadFile(context, getString(R.string.daysSavefile));
        if (loadDayHours != null) {
            for (Integer key : loadDayHours.keySet()) {
                map.put(key, true);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayshours);
        getSupportActionBar().hide();

        initializeDayHour(this);

        /*
        El int prev indica si la ventana anterior es:
            -El menu principal (1)
            -La entrada (0).
         */
        if (getIntent().getExtras() != null) {
            prev = Integer.parseInt(getIntent().getStringExtra("prev"));
        }

        // Se buscan el widget y el boton.
        final Context context = DayHourActivity.this;
        final WeekdaysPicker widget = findViewById(R.id.weekdays);
        final Button saveDaysBtn = findViewById(R.id.saveDaysButton);

        // Se insertan los dias del inicializador al widget.
        widget.setCustomDays(map);

        /*
        En el caso de que la ventana anterior sea el de la entrada, eso significa que no
        existe un archivo que tenga dias guardados, por lo tanto todos los dias estan
        des-seleccionados y el boton de guardar debe estar desactivado.
         */
        if (prev == 0) {
            saveDaysBtn.setClickable(false);
            saveDaysBtn.setAlpha((float) 0.25);
        }

        widget.setOnWeekdaysChangeListener(new OnWeekdaysChangeListener() {
            @Override
            public void onChange(View view, int clickedDayOfWeek, List<Integer> selectedDays) {
                /*
                Si el dia que fue seleccionado en el widget esta activado,
                se actualiza el valor del dia con lo que se introduzca en el reloj.
                 */
                if (selectedDays.contains(clickedDayOfWeek)) {
                    calListStart.put(clickedDayOfWeek, DayHourController.getDayHour(context, clickedDayOfWeek));
                }

                // Si el widget esta vacio, el boton de guardar esta desactivado.
                if (widget.noDaySelected()) {
                    saveDaysBtn.setClickable(false);
                    saveDaysBtn.setAlpha((float) 0.25);
                } else {
                    saveDaysBtn.setClickable(true);
                    saveDaysBtn.setAlpha((float) 1.0);
                }
            }
        });
    }

    public void saveDays(View v) {
        // Se sacan todos los dias seleccionados del widget.
        final WeekdaysPicker widget = findViewById(R.id.weekdays);
        List<Integer> selectedDaysInt = widget.getSelectedDays();
        HashMap<Integer, Calendar> saveDays = new HashMap<>();

        // Se introducen los dias seleccionados en un HashMap.
        for (int i = 0; i < selectedDaysInt.size(); i++) {
            saveDays.put(selectedDaysInt.get(i), calListStart.get(selectedDaysInt.get(i)));
        }

        // Se guardan los dias en el HashMap.
        SaveLoadController.saveFile(saveDays, this.getApplicationContext(), getString(R.string.daysSavefile));

        // Se crean las alarmas para las notificaciones en base a los dias del HashMap.
        NotificationController.scheduleNotification(this, saveDays);

        // Se le notifica al usuario que se guardaron los dias.
        Toast toast = Toast.makeText(getApplicationContext(),
                (R.string.daysSaved), Toast.LENGTH_SHORT);
        toast.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent;

                /*
                Si la ventana anterior era la de la entrada, esto significa que no hay un
                archivo donde esten los dias guardados y, por lo tanto, tampoco de los lugares.
                Por lo cual, el siguiente menu debe de ser el de los lugares,

                De lo contrario, la siguiente ventana sera el menu principal.
                 */
                if (prev == 0) {
                    mainIntent = new Intent(DayHourActivity.this, PlacesActivity.class);
                } else {
                    mainIntent = new Intent(DayHourActivity.this, HomeActivity.class);
                }
                mainIntent.putExtra("prev", "0");
                DayHourActivity.this.startActivity(mainIntent);
                DayHourActivity.this.finish();
            }
        }, 100);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = null;

                /*
                Si la ventana anterior era la de la entrada, si se le da back no debe de mandarlo
                a ninguna parte y la aplicacion se ha de cerrar.

                De lo contrario, la siguiente ventana sera la de los lugares.
                 */
                if (prev != 0) {
                    mainIntent = new Intent(DayHourActivity.this, PlacesActivity.class);
                }
                DayHourActivity.this.startActivity(mainIntent);
                DayHourActivity.this.finish();
            }
        }, 100);
    }
}