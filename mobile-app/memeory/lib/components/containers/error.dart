import 'package:flutter/material.dart';
import 'package:memeory/util/consts/consts.dart';
import 'package:memeory/util/i18n/i18n.dart';

class ErrorContainer extends StatelessWidget {
  const ErrorContainer({Key key, this.error, this.reload}) : super(key: key);

  final dynamic error;
  final VoidCallback reload;

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
              text: t(context, "error_occurred"),
              style: TextStyle(
                fontSize: 13.0,
                fontWeight: FontWeight.bold,
                color: Theme.of(context).errorColor,
              ),
              children: <TextSpan>[
                TextSpan(
                  text: error?.toString() ?? EMPTY,
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
              ? Container()
              : RaisedButton(
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(24),
                  ),
                  onPressed: reload,
                  padding: EdgeInsets.all(12),
                  child: Text(
                    t(context, "retry"),
                    style: TextStyle(color: Colors.white),
                  ),
                ),
        )
      ],
    );
  }
}
