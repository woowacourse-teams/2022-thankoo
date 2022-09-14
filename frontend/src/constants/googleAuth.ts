const OAuth = {
  GOOGLE_URL: 'https://accounts.google.com/o/oauth2/v2/auth',
  CLIENT_ID: '',
  REDIRECT_URL: '',
  RESPONSE_TYPE: 'response_type=code',
  SCOPE:
    'scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile openid',
};

console.log(process.env);
switch (process.env.MODE) {
  case 'noServer':
    OAuth.CLIENT_ID = '';
    OAuth.REDIRECT_URL = '';
    break;
  case 'local':
    OAuth.CLIENT_ID =
      'client_id=227589352657-r8isqlltgtbs3du5nn7uc5674kt6t3rq.apps.googleusercontent.com';
    OAuth.REDIRECT_URL = 'redirect_uri=http://localhost:3000/sign-in';
    break;
  case 'development':
    OAuth.CLIENT_ID =
      'client_id=119823899527-vi7230j5vt9lf8s4aiapcao4pf4n9d98.apps.googleusercontent.com';
    OAuth.REDIRECT_URL =
      'redirect_uri=http://ec2-13-209-75-154.ap-northeast-2.compute.amazonaws.com/sign-in';
    break;
  case 'production':
    OAuth.CLIENT_ID =
      'client_id=135992368964-20ad4ul4e3mmia6iok3r9dpg6bshp4uq.apps.googleusercontent.com';
    OAuth.REDIRECT_URL = 'redirect_uri=https://thankoo.co.kr/sign-in';
    break;
  default:
    break;
}
console.log(OAuth);
const { GOOGLE_URL, CLIENT_ID, REDIRECT_URL, RESPONSE_TYPE, SCOPE } = OAuth;
export const GOOGLE_AUTH_URL = `${GOOGLE_URL}?${CLIENT_ID}&${REDIRECT_URL}&${RESPONSE_TYPE}&${SCOPE}`;
