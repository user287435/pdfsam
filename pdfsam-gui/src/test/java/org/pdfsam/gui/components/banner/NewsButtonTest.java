/*
 * This file is part of the PDF Split And Merge source code
 * Created on 26 ott 2015
 * Copyright 2017 by Sober Lemur S.a.s. di Vacondio Andrea (info@pdfsam.org).
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.pdfsam.gui.components.banner;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pdfsam.eventstudio.Listener;
import org.pdfsam.model.news.HideNewsPanelRequest;
import org.pdfsam.model.news.ShowNewsPanelRequest;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.pdfsam.eventstudio.StaticStudio.eventStudio;

/**
 * @author Andrea Vacondio
 */
@ExtendWith({ ApplicationExtension.class })
public class NewsButtonTest {
    private NewsButton victim;
    private FxRobot robot;

    @Start
    public void start(Stage stage) {
        victim = new NewsButton();
        Scene scene = new Scene(victim);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void onClick() {
        Listener<ShowNewsPanelRequest> listener = mock(Listener.class);
        Listener<HideNewsPanelRequest> hideListener = mock(Listener.class);
        eventStudio().add(ShowNewsPanelRequest.class, listener);
        eventStudio().add(HideNewsPanelRequest.class, hideListener);
        robot.clickOn(".button");
        verify(listener).onEvent(eq(ShowNewsPanelRequest.INSTANCE));
        verify(hideListener, never()).onEvent(any());
        robot.clickOn(".button");
        verify(hideListener).onEvent(eq(HideNewsPanelRequest.INSTANCE));
    }

    @Test
    public void setUpToDate() {
        assertFalse(victim.getStyleClass().contains(NewsButton.UP_TO_DATE_CSS_CLASS));
        victim.setUpToDate(false);
        assertTrue(victim.getStyleClass().contains(NewsButton.UP_TO_DATE_CSS_CLASS));
        victim.setUpToDate(true);
        assertFalse(victim.getStyleClass().contains(NewsButton.UP_TO_DATE_CSS_CLASS));
    }

}