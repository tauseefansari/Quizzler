package com.tauseef.quizzler;

public class TrueFalse {
    private int mQuestionID;
    private boolean mAnswer;

    public TrueFalse(int mQuestionReferenceID, boolean trueFalse)
    {
        mQuestionID = mQuestionReferenceID;
        mAnswer = trueFalse;
    }

    public int getQuestionID() {
        return mQuestionID;
    }

    public void setQuestionID(int questionID) {
        mQuestionID = questionID;
    }

    public boolean isAnswer() {
        return mAnswer;
    }

    public void setAnswer(boolean answer) {
        mAnswer = answer;
    }
}
