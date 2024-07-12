package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.semconv.SemanticAttributes;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;

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
      Span childSpan = tracer.spanBuilder("child").startSpan();

      try (Scope scope = childSpan.makeCurrent()){
        int res = ThreadLocalRandom.current().nextInt(
          this.min, this.max+1
        ); 
        childSpan.addEvent("Init");
        Attributes eventAttributes = Attributes.of(
          AttributeKey.stringKey("key"), "value",
          AttributeKey.longKey("result"), 0l); 
        childSpan.addEvent("End", eventAttributes);
        return res;
      } finally{
        childSpan.end();
      }
    }

    public List<Integer> rollTheDice(int rolls) {
      Span paretSpan = tracer.spanBuilder(
        "parent"
      ).startSpan();

      List<Integer> results = new ArrayList<Integer>();
      try (Scope scope = paretSpan.makeCurrent()){
        Context context = Context.current();
        for (int i = 0; i < rolls; i++) {
          Thread thread = new Thread(
          context.wrap(new Runnable() {
            @Override
            public void run(){
              results.add(this.rollOnce());
            }
          })
        );
        }
      } catch (Throwable throwable) {
          paretSpan.setStatus(StatusCode.ERROR, "Something bad happened!");
          paretSpan.recordException(throwable);
      } finally {
        paretSpan.end();
      };
      return results;
  }
}
