import { Card, Avatar } from 'antd';
import { BgColorsOutlined, EditOutlined } from '@ant-design/icons';
import ScheduleButton from './ScheduleButton';
import MyResponsiveLine from './Chart';
import React from 'react';

const { Meta } = Card;

const PlantCard = (props) => {

  const [schedule, setSchedule] = React.useState([])
  //TODO Figure out how to properly set the state in reaction to new server events via a react hook 
  let fetchSchedules = (pinId) => {
    let d = new Date().toISOString()
    const query = `http://localhost:9000/schedules?pinId=${pinId}&startTime=${d}&duration=10`;
    return fetch(query).then(res => res.json()).then(res => {
      console.log(res)
      let xyRes =  res.map((timeAndVolume) => ({ x: timeAndVolume.time, y: timeAndVolume.volume }))
      setSchedule(xyRes)
      console.log(xyRes)
      return xyRes
    })
  }
  // fetchSchedules(props.pinId)
  // console.log(schedule)
  let serverResMapping = {
    "🌵": [
      {
        "x": "2019-12-22T14:43:00.000",
        "y": 0
      },
      {
        "x": "2019-12-22T14:44:00.000",
        "y": 0
      },
      {
        "x": "2019-12-22T14:45:00.000",
        "y": 1
      },
      {
        "x": "2019-12-22T14:46:00.000",
        "y": 0
      },
      {
        "x": "2019-12-22T14:47:00.000",
        "y": 0
      },
      {
        "x": "2019-12-22T14:48:00.000",
        "y": 0
      },
      {
        "x": "2019-12-22T14:49:00.000",
        "y": 0
      },
      {
        "x": "2019-12-22T14:50:00.000",
        "y": 1
      },
      {
        "x": "2019-12-22T14:51:00.000",
        "y": 0
      },
      {
        "x": "2019-12-22T14:52:00.000",
        "y": 0
      }
    ], 
    "🌱": [
      
        {
            "x": "2019-12-22T14:43:00.000",
            "y": 0
        },
        {
            "x": "2019-12-22T14:44:00.000",
            "y": 1
        },
        {
            "x": "2019-12-22T14:45:00.000",
            "y": 0
        },
        {
            "x": "2019-12-22T14:46:00.000",
            "y": 1
        },
        {
            "x": "2019-12-22T14:47:00.000",
            "y": 0
        },
        {
            "x": "2019-12-22T14:48:00.000",
            "y": 1
        },
        {
            "x": "2019-12-22T14:49:00.000",
            "y": 0
        },
        {
            "x": "2019-12-22T14:50:00.000",
            "y": 1
        },
        {
            "x": "2019-12-22T14:51:00.000",
            "y": 0
        },
        {
            "x": "2019-12-22T14:52:00.000",
            "y": 1
        }
    ]

  }
  let data = [{
    "id": "WateringTimes",
    "color": "hsl(98, 70%, 50%)",
    "data": schedule
  }]
  return (
    <Card
      style={{ width: 500, margin: 16, display: 'inline-block' }}
      cover={
        <img
          alt="cactus"
          src="https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/cactus-plant-pot-stand-1599155636.jpg"
        />
      }
      actions={[
        <ScheduleButton />,
        <EditOutlined key="edit" />,
        <BgColorsOutlined key="water" disabled={true} />,
      ]}
    >
      <Meta
        avatar={
          <Avatar src="https://avatars0.githubusercontent.com/u/4334491?s=460&v=4" />
        }
        title="One Very Happy Plant"
        description={
          <>
            <p>Plant ID: {props.id}</p>
            <p>Pin ID: {props.pinId}</p>
            <p>Plant Type: {props.plantType}</p>
            <p>Volume: {props.volume}</p>
            <p>Schedule: {schedule}</p>
          </>
        }
      />
      <div className="LineGraph" style={{ height: 200 }}>
        {MyResponsiveLine({ data })}</div>
    </Card>
  )
};

export default PlantCard;
