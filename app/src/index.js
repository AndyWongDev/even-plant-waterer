import React from 'react';
import ReactDOM from 'react-dom';
import App from './components/App';

import parser from 'cron-parser';
import cronTime from 'cron-time-generator';

ReactDOM.render(<App />, document.getElementById('root'));

// console.log(cronTime.everyMinute());

// try {
//   const interval = parser.parseExpression('*/2 * * * *');

//   console.log('Date: ', interval.next().toString()); // Sat Dec 29 2012 00:42:00 GMT+0200 (EET)
//   console.log('Date: ', interval.next().toString()); // Sat Dec 29 2012 00:44:00 GMT+0200 (EET)

//   console.log('Date: ', interval.prev().toString()); // Sat Dec 29 2012 00:42:00 GMT+0200 (EET)
//   console.log('Date: ', interval.prev().toString()); // Sat Dec 29 2012 00:40:00 GMT+0200 (EET)
// } catch (err) {
//   console.log('Error: ' + err.message);
// }

// const sampleCron = cronTime.everyWeekAt(3, 12, 22);
// console.log(sampleCron);
// const parsedCronExpression = parser.parseExpression(sampleCron);
// console.log(
//   'Next Interval',
//   parsedCronExpression.next(parsedCronExpression).toString(),
// );
