package com.Hackathon.phone2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;
import android.nfc.Tag;
import android.nfc.tech.Ndef;

import java.io.UnsupportedEncodingException;
import java.lang.String;

public class MainActivity extends Activity {

	private NfcAdapter mNfcAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

		// if (mNfcAdapter.isEnabled()) {
		// Context context = getApplicationContext();
		// CharSequence text = "NFC Enabled";
		// int duration = Toast.LENGTH_SHORT;
		// Toast toast = Toast.makeText(context, text, duration);
		// toast.show();
	}

	@Override
	protected void onResume() {

		super.onResume();

		if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())
				|| NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent()
						.getAction())) {

			Context context = getApplicationContext();
			CharSequence text = "NFC Read";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();

			Tag tag = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
			Ndef ndef = Ndef.get(tag);
			NdefMessage ndefMessage = ndef.getCachedNdefMessage();
			NdefRecord[] records = ndefMessage.getRecords();
			//Toast toast2 = Toast.makeText(getApplicationContext(), new Integer(records.length).toString(), Toast.LENGTH_SHORT);
			//toast2.show();
			String[] nfctexts=new String[7];
			final EditText text1 = (EditText) findViewById(R.id.Name);
			final EditText text2 = (EditText) findViewById(R.id.DOB);
			final EditText text3 = (EditText) findViewById(R.id.ICEName);
			final EditText text4 = (EditText) findViewById(R.id.Phone);
			final EditText text5 = (EditText) findViewById(R.id.Allergies);
			final EditText text6 = (EditText) findViewById(R.id.mc);
			for (NdefRecord ndefRecord : records) {
					try {
						String nfctext = readText(ndefRecord);
						nfctexts=nfctext.split("~");
						text1.setText(nfctexts[1]);
						text2.setText(nfctexts[2]);
						text3.setText(nfctexts[4]);
						text4.setText(nfctexts[3]);
						text5.setText(nfctexts[5]);
						text6.setText(nfctexts[6]);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


	                }
	            }
	        }


public String readText(NdefRecord record)
			throws UnsupportedEncodingException {

		byte[] payload = record.getPayload();

		String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";

		int languageCodeLength = payload[0] & 0063;

		return new String(payload, languageCodeLength + 1, payload.length
				- languageCodeLength - 1, textEncoding);
	}
}
// public void onResume() {
// super.onResume();
// Intent intent=getIntent();
// if (mNfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction()) ||
// mNfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {
// Context context = getApplicationContext();
// CharSequence text = "NFC Scanned";
// int duration = Toast.LENGTH_SHORT;
// Toast toast = Toast.makeText(context, text, duration);
// toast.show();
// Parcelable[] rawMsgs =
// intent.getParcelableArrayExtra(mNfcAdapter.EXTRA_NDEF_MESSAGES);
// if (rawMsgs != null) {
// NdefMessage[] msgs = new NdefMessage[rawMsgs.length];
// for (int i = 0; i < rawMsgs.length; i++) {
// msgs[i] = (NdefMessage) rawMsgs[i];
// }
// }
// }
// //process the msgs array
// }
// }
