function packedData = packInt64(data)
%packInt32 Internal function to cast an int64 into 
%	an uint8 array

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: packInt64.m 31 2016-09-28 15:16:56Z afacostag $

% if isa(data,'int64')
	packedData = fliplr(typecast(data,'uint8'));
% else
% 	packedData = fliplr(typecast(int64(data),'uint8'));
% end