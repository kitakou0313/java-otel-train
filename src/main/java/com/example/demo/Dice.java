package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.OpenTelemetry;

public class Dice {
    private int min;
    private int max;
    private Tracer tracer;

    public Dice(int min, int max, OpenTelemetry openTelemetry){
        this.min = min;
        this.max = max;

        this.tracer = openTelemetry.getTracer(
          Dice.class.getName(), "0.1.0"
        );
    }

    public Dice(int min, int max){
      this(min, max, OpenTelemetry.noop());
    }

    private int rollOnce(){
        return ThreadLocalRandom.current().nextInt(
            this.min, this.max+1
        );
    }

    public List<Integer> rollTheDice(int rolls) {
    List<Integer> results = new ArrayList<Integer>();
    for (int i = 0; i < rolls; i++) {
      results.add(this.rollOnce());
    }
    return results;
  }
}
