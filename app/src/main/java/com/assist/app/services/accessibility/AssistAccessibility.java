package com.assist.app.services.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.Iterator;
import java.util.Set;

public class AssistAccessibility extends AccessibilityService {
    public static final String TAG = AssistAccessibility.class.getName();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "AssistAccessibility started");
        return super.onStartCommand(intent, flags, startId);
    }

    public AssistAccessibility() {
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent != null) {

            Log.d(TAG, "=========================");
            String nodePackageName = accessibilityEvent.getPackageName().toString();
            Log.d(TAG, nodePackageName);

            String nodeContentDescription = accessibilityEvent.getContentDescription() != null ?
                    accessibilityEvent.getContentDescription().toString()
                    :"getContentDescription=null";
            Log.d(TAG, nodeContentDescription);

            int eventType = accessibilityEvent.getEventType();
            displayEventType(eventType, accessibilityEvent);

            int action = accessibilityEvent.getAction();
            //displayEventType(eventType);

            if (accessibilityEvent.getSource() != null)
            displayNodeInfo(getRootInActiveWindow());

            Log.d(TAG, "=========================");



        }
    }

    private void displayEventType(int eventType, AccessibilityEvent event) {
        switch (eventType) {
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_END: Log.d(TAG, "TYPE_GESTURE_DETECTION_END");break;
            //case AccessibilityEvent.CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION: Log.d(TAG, "");
            //case AccessibilityEvent.CONTENT_CHANGE_TYPE_TEXT: Log.d(TAG, "");
            case AccessibilityEvent.CONTENT_CHANGE_TYPE_UNDEFINED: Log.d(TAG, "CONTENT_CHANGE_TYPE_UNDEFINED");break;
            case AccessibilityEvent.INVALID_POSITION: Log.d(TAG, "INVALID_POSITION");break;
            case AccessibilityEvent.TYPE_ANNOUNCEMENT: Log.d(TAG, "TYPE_ANNOUNCEMENT");break;
            case AccessibilityEvent.TYPE_ASSIST_READING_CONTEXT: Log.d(TAG, "TYPE_ASSIST_READING_CONTEXT");break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_START: Log.d(TAG, "TYPE_GESTURE_DETECTION_START");break;
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED: Log.d(TAG, "TYPE_NOTIFICATION_STATE_CHANGED");
            readNotification(event);
            break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END: Log.d(TAG, "TYPE_TOUCH_EXPLORATION_GESTURE_END");break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START: Log.d(TAG, "TYPE_TOUCH_EXPLORATION_GESTURE_START");break;
            case AccessibilityEvent.TYPE_TOUCH_INTERACTION_END: Log.d(TAG, "TYPE_TOUCH_INTERACTION_END");break;
            case AccessibilityEvent.TYPE_TOUCH_INTERACTION_START: Log.d(TAG, "TYPE_TOUCH_INTERACTION_START");break;
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED: Log.d(TAG, "TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED");break;
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED: Log.d(TAG, "TYPE_VIEW_ACCESSIBILITY_FOCUSED");break;
            case AccessibilityEvent.TYPE_VIEW_CLICKED: Log.d(TAG, "TYPE_VIEW_CLICKED");break;
            case AccessibilityEvent.TYPE_VIEW_CONTEXT_CLICKED: Log.d(TAG, "TYPE_VIEW_CONTEXT_CLICKED");break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED : Log.d(TAG, "TYPE_VIEW_FOCUSED");break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER : Log.d(TAG, "TYPE_VIEW_HOVER_ENTER");break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT : Log.d(TAG, "TYPE_VIEW_HOVER_EXIT");break;
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED : Log.d(TAG, "TYPE_VIEW_LONG_CLICKED");break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED : Log.d(TAG, "TYPE_VIEW_SCROLLED");break;
            case AccessibilityEvent.TYPE_VIEW_SELECTED : Log.d(TAG, "TYPE_VIEW_SELECTED");break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED : Log.d(TAG, "TYPE_VIEW_TEXT_CHANGED");break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED : Log.d(TAG, "TYPE_VIEW_TEXT_SELECTION_CHANGED");break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY : Log.d(TAG, "TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY");break;
            case AccessibilityEvent.TYPE_WINDOWS_CHANGED : Log.d(TAG, "TYPE_WINDOWS_CHANGED");break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED : Log.d(TAG, "TYPE_WINDOW_CONTENT_CHANGED");break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED : Log.d(TAG, "TYPE_WINDOW_STATE_CHANGED");break;
            default:Log.d(TAG, "Unknown Event-"+eventType);


        }

    }

    private void readNotification(AccessibilityEvent event) {
        Parcelable parcelable = event.getParcelableData();
        Notification notification = (Notification) parcelable;

        if (notification != null) {
            Bundle bundle = notification.extras;
            if (bundle != null) {
                Set<String> keys = bundle.keySet();
                Iterator<String> itr = keys.iterator();
                while (itr.hasNext()) {
                    String key = itr.next();
                    Object value =   bundle.get(key);
                    if (value != null)
                    Log.d(TAG, key+"-"+value.toString());

                }
            }
        }


    }

    private void displayNodeInfo(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo != null) {
            String parentNodeClazzName = nodeInfo.getClassName() !=null ? nodeInfo.getClassName().toString(): null;
            Log.d(TAG, "Parent Node Clazz Name:"+parentNodeClazzName);

            String nodeText = nodeInfo.getText() != null? nodeInfo.getText().toString() : null;
            Log.d(TAG, "Parent Node Text:"+nodeText);

            int nodeChildCount = nodeInfo.getChildCount();
            if (nodeChildCount>0) {
                for (int i=0; i<nodeChildCount;i++) {
                    AccessibilityNodeInfo childNode = nodeInfo.getChild(i);
                    if (childNode != null)
                        displayChildNode(childNode);
                }
            }


        }
    }

    private void displayChildNode(AccessibilityNodeInfo nodeInfo) {

        String childNodeClazzName = nodeInfo.getClassName() !=null ? nodeInfo.getClassName().toString(): null;
        Log.d(TAG, "Child Node Clazz Name:"+childNodeClazzName);

        String nodeText = nodeInfo.getText() != null? nodeInfo.getText().toString() : null;
        Log.d(TAG, "Child Node Text:"+nodeText);

        int childCount = nodeInfo.getChildCount();
        if (childCount>0) {
            for (int i=0; i<childCount;i++) {
                AccessibilityNodeInfo childNode = nodeInfo.getChild(i);
                if (childNode != null)
                    displayChildNode(childNode);
            }
        }


    }

    @Override
    public void onInterrupt() {

    }

}
