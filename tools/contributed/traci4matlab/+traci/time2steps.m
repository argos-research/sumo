function timeInMiliseconds = time2steps(time)
%timeInMiliseconds An internal function to convert time in seconds to
%miliseconds.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: time2steps.m 31 2016-09-28 15:16:56Z afacostag $

timeInMiliseconds = int32(time*1000);