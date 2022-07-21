const timesByThirty: string[] = [];

for (let i = 1; i < 25; i += 1) {
  for (let j = 0; j < 2; j += 1) {
    timesByThirty.push(`${String(i).padStart(2, '0')}:${String(j * 30).padStart(2, '0')}`);
  }
}

const Time = ({ value, onChange, required, ...props }) => {
  return (
    <>
      <input
        {...props}
        id='appt-time'
        list='times'
        type='time'
        name='appt-time'
        value={value}
        onChange={onChange}
        step='1800'
        required={required}
      />
      <datalist id='times'>
        {timesByThirty.map(time => (
          <option key={time} value={time} />
        ))}
      </datalist>
    </>
  );
};

export default Time;
