import React from 'react';
import ReactDOM from 'react-dom';
import App from './components/App';

import parser from 'cron-parser';
import cronTime from "cron-time-generator";

ReactDOM.render(<App />, document.getElementById('root'));
const query = 'http://localhost:9000/schedules?pinId=123&startTime=2020-01-01T00:00:00Z&duration=10';
const x = fetch(query).then(res => {
    console.log(res.json())
    return JSON.stringify(res)
})
console.log(x)

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

const sampleCron = cronTime.everyWeekAt(3, 12, 22)
console.log(sampleCron)
const parsedCronExpression = parser.parseExpression(sampleCron);
console.log("Next Interval", parsedCronExpression.next(parsedCronExpression).toString())