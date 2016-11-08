classdef Socket < handle
%Socket A class which wraps a Java socket to implement
%	TCP/IP communications in Matlab.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: Socket.m 31 2016-09-28 15:16:56Z afacostag $

	properties
		socket
		dis
		dos
		dataReader
	end
	methods
		function this = connect(this, host, port)
			import co.edu.unalmed.gaunal.traci4matlab.utils.*
			import java.net.*
			import java.io.*
			this.socket = Socket();
			inetSocketAddress = InetSocketAddress(...
				InetAddress.getByName(host), port);
			try
				this.socket.connect(inetSocketAddress);
			catch err
                disp(err.getReport);
%                 throw(MException('Connection:ConnectionRefused', err.getReport));
			end
			this.dis = DataInputStream(this.socket.getInputStream());
			this.dataReader = DataReader(this.dis);
			this.dos = DataOutputStream(this.socket.getOutputStream());
		end
	end
end