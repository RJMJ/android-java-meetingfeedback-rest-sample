/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.event;

public class SendRatingSuccessEvent {
    public String mEventId;

    public SendRatingSuccessEvent(String eventId) {
        mEventId = eventId;
    }
}
