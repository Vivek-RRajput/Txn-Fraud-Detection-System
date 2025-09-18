from flask import Flask, request, jsonify
import joblib
import pandas as pd

app = Flask(__name__)
fraud_model = joblib.load("fraud_model.pkl")

def preprocess_transaction(txn):
    df = pd.DataFrame([txn])
    df['timestamp'] = pd.to_datetime(df['timestamp'])
    df['hour'] = df['timestamp'].dt.hour
    df['day'] = df['timestamp'].dt.dayofweek
    df['email_domain'] = df['email'].apply(lambda x: x.split('@')[-1] if x else "unknown")
    df = pd.get_dummies(df, columns=['type', 'location', 'email_domain'], drop_first=True)
    df = df.drop(columns=['transactionId','accountId','timestamp','email'], errors='ignore')
    
    # align columns
    missing_cols = set(fraud_model.feature_names_in_) - set(df.columns)
    for col in missing_cols:
        df[col] = 0
    df = df[fraud_model.feature_names_in_]
    
    return df

@app.route('/predict_fraud', methods=['POST'])
def predict_fraud():
    txn = request.json
    df = preprocess_transaction(txn)
    pred = fraud_model.predict(df)
    txn['fraud_flag'] = "Fraud" if pred[0] == -1 else "Normal"
    return jsonify(txn)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)

