using System;

public class PinpointTypes
{
	public PinpointTypes()
	{
		public int pinTypeId { get; set; }
		public int pinpointName { get; set; }
		public string pinpointIcon { get; set; }

		public virtual ICollection<PinpointTypes> PinpointTypes { get; set; }
	}
}
