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
        childSpan.setAttribute("res", res);
        return res;
      } finally{
        childSpan.end();
      }
    }

    public List<Integer> rollTheDice(int rolls) {
      Span parentSpan = tracer.spanBuilder(
        "parent"
      ).startSpan();

      List<Integer> results = new ArrayList<Integer>();
      // makeCurrentにより，Span.current()で返されるSpan ClassのObjectが更新される
      try (Scope scope = parentSpan.makeCurrent()){
        for (int i = 0; i < rolls; i++) {
          results.add(this.rollOnce());
        }
      } catch (Throwable throwable) {
          parentSpan.setStatus(StatusCode.ERROR, "Something bad happened!");
          parentSpan.recordException(throwable);
      } finally {
        parentSpan.end();
      };
      return results;
  }
}
