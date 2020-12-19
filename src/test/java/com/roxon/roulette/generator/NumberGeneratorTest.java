package com.roxon.roulette.generator;

import com.roxon.roulette.game.Game;
import java.lang.reflect.Method;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;

@RunWith(PowerMockRunner.class)
@PrepareForTest({NumberGenerator.class,Game.class})
public class NumberGeneratorTest {

  @Test
  public void testMethod1Invocation() throws Exception {

    spy(NumberGenerator.class);

    Game gameMock = PowerMockito.mock(Game.class);

    Method finishRound = NumberGenerator.class.getDeclaredMethod(
        "finishRound",
        Game.class
    );

    finishRound.setAccessible(true);
    finishRound.invoke(NumberGenerator.class, gameMock);
    verify(gameMock, times(1)).finishRound(anyInt());
  }
}
