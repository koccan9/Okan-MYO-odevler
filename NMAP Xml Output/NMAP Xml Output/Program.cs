using System;
using System.IO;
using System.Diagnostics;

namespace NMAP_Xml_Output
{
    class Program
    {
        static void Main(string[] args)
        {
            var p_info = new ProcessStartInfo();
            p_info.FileName = "nmap";
            p_info.Arguments = "-F 127.0.0.1 -oX stdout.xml";
            p_info.CreateNoWindow = true;
            var p = new Process();
            p.StartInfo = p_info;
            p.Start();
            Console.WriteLine("nmap was started");
            p.WaitForExit();
            Console.WriteLine(p.ExitCode);
            Console.ReadKey(true);
        }
    }
}
