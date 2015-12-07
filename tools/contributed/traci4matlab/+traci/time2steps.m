function timeInMiliseconds = time2steps(time)
%timeInMiliseconds An internal function to convert time in seconds to
%miliseconds.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

timeInMiliseconds = int32(time*1000);