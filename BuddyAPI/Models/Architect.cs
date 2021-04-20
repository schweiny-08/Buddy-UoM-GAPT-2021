using System;

public class Architect
{
	public Architect()
	{
		public int architectId { get; set; }
		public int userId { get; set; }

		public virtual ICollection<Architect> Architect { get; set; }
	}
}
