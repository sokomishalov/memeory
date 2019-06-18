import 'package:flutter/material.dart';

class ErrorContainer extends StatelessWidget {
  const ErrorContainer({Key key, this.error, this.reload()}) : super(key: key);

  final error;
  final reload;

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.center,
      mainAxisAlignment: MainAxisAlignment.center,
      children: <Widget>[
        Icon(
          Icons.error,
          size: 50,
          color: Theme.of(context).errorColor,
        ),
        Container(
          padding: EdgeInsets.only(left: 20, right: 20, top: 20),
          child: RichText(
            text: TextSpan(
              text: "Возникла ошибка: ",
              style: TextStyle(
                fontSize: 13.0,
                fontWeight: FontWeight.bold,
                color: Theme.of(context).errorColor,
              ),
              children: <TextSpan>[
                TextSpan(
                  text: error?.toString() ?? "",
                  style: TextStyle(
                    fontWeight: FontWeight.normal,
                    color: Theme.of(context).textTheme.title.color,
                  ),
                )
              ],
            ),
          ),
        ),
        Container(
          padding: EdgeInsets.only(top: 20),
          child: reload == null
              ? Text(
                  "Попробуйте перезапустить приложение",
                  style: TextStyle(
                    fontSize: 13.0,
                    color: Theme.of(context).textTheme.title.color,
                  ),
                )
              : RaisedButton(
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(24),
                  ),
                  onPressed: reload,
                  padding: EdgeInsets.all(12),
                  child: Text(
                    'Повторить попытку',
                    style: TextStyle(color: Colors.white),
                  ),
                ),
        )
      ],
    );
  }
}
