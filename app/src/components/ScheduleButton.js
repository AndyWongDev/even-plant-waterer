import { ClockCircleOutlined } from '@ant-design/icons';
import { Button, InputNumber, Popover, Select } from 'antd';
const { Option } = Select;

const scheduleState = {
  unit: 'week',
  value: 1,
};

const NumberDropdown = () => (
  <InputNumber
    min={0}
    max={31}
    defaultValue={1}
    style={{ width: 60 }}
    onChange={(e) => (scheduleState.value = e)}
  />
);

const ScheduleDropdown = () => (
  <>
    <Select
      defaultValue="week"
      style={{ width: 120 }}
      onChange={(e) => (scheduleState.unit = e)}
    >
      <Option value="day">day(s)</Option>
      <Option value="week">week(s)</Option>
      <Option value="month">month(s)</Option>
    </Select>
  </>
);

const onSaveAction = (e) => {
  console.log(scheduleState);
};

const SaveButton = () => (
  <Button type="primary" onClick={onSaveAction}>
    Save
  </Button>
);

const scheduleContent = (
  <div>
    <>Every: </>
    <NumberDropdown />
    <ScheduleDropdown />
    <SaveButton />
  </div>
);

const ScheduleButton = () => (
  <>
    <Popover content={scheduleContent} title="Change Schedule">
      <ClockCircleOutlined key="schedule" />
    </Popover>
  </>
);

export default ScheduleButton;
