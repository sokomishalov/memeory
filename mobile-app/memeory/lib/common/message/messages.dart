import 'package:flutter/material.dart';
import 'package:toast/toast.dart';

errorMessage(message, context) {
  showDialog(
    context: context,
    builder: (context) => AlertDialog(
      title: Text("Ошибка"),
      content: Text(message ?? ""),
      actions: <Widget>[
        // usually buttons at the bottom of the dialog
        new FlatButton(
          child: new Text("Закрыть"),
          onPressed: () {
            Navigator.of(context).pop();
          },
        ),
      ],
    ),
  );
}

withConfirmation(message, yesAction, context) {
  showDialog(
    context: context,
    builder: (context) => AlertDialog(
      title: Text("Подтверждение"),
      content: Text(message ?? ""),
      actions: <Widget>[
        FlatButton(
          child: Text("Нет"),
          onPressed: () {
            Navigator.of(context).pop();
          },
        ),
        FlatButton(
          child: Text("Да"),
          onPressed: () {
            yesAction();
            Navigator.of(context).pop();
          },
        )
      ],
    ),
  );
}

infoToast(message, context) {
  Toast.show(
    message ?? "",
    context,
  );
}

successToast(message, context) {
  Toast.show(
    message ?? "",
    context,
  );
}

errorToast(message, context) {
  Toast.show(
    "Ошибка: " + message ?? "Неизвестная ошибка",
    context,
    duration: 3,
  );
}
