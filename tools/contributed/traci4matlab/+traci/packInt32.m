function packedData = packInt32(data)
%packInt32 Internal function to cast an int32 into 
%	an uint8 array

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: packInt32.m 31 2016-09-28 15:16:56Z afacostag $

if isa(data,'int32')
	packedData = fliplr(typecast(data,'uint8'));
else
	packedData = fliplr(typecast(int32(data),'uint8'));
end