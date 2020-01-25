import * as functions from 'firebase-functions';
import * as admin from 'firebase-admin';

admin.initializeApp()

// exports.addMessage = functions.https.onRequest(async (req, res) => {
//     const original = req.query.text;

//     const snapshot = await admin.database().ref('/messages').push({original});

//     res.redirect(303, snapshot.ref.toString())
// })