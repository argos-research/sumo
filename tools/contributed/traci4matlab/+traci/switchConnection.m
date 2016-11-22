function switchConnection(label)
%switchConnection Switch to another connection with SUMO.
%
%   switchConnection(label) Switch to the connection specified in LABEL 
%   with the SUMO server.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: switchConnection.m 31 2016-09-28 15:16:56Z afacostag $

global connections
connections('') = connections(label);